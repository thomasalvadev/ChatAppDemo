package ominext.com.echo.model.httpclient;

import android.content.Context;
import android.util.Base64;
import android.util.Log;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.lang.reflect.Type;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import io.realm.RealmObject;
import okhttp3.CipherSuite;
import okhttp3.ConnectionSpec;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.TlsVersion;
import okhttp3.logging.HttpLoggingInterceptor;
import ominext.com.echo.appinterface.OnResponseSuccess;
import ominext.com.echo.model.login.LoginResponse;
import ominext.com.echo.model.message.ListMessageInfo;
import ominext.com.echo.model.message.Message;
import ominext.com.echo.model.timeline.TimelineResponse;
import ominext.com.echo.utils.ConfigManager;
import ominext.com.echo.utils.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by dieunv on 4/25/2016.
 */
public class HttpUtil extends HttpUtilsBase {

    // private static final String TAG = HttpUtil.class.getSimpleName();
    private static HttpUtil mInstance;
    private static HttpUtil mInstanceAPI;
    private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")

            .setExclusionStrategies(new ExclusionStrategy() {
                @Override
                public boolean shouldSkipField(FieldAttributes f) {
                    return f.getDeclaringClass().equals(RealmObject.class);
                }

                @Override
                public boolean shouldSkipClass(Class<?> clazz) {
                    return false;
                }
            }).serializeNulls()
            .create();
    private Retrofit retrofit;
    private APIService service;

    private ConfigManager configManager;

    public static HttpUtil getInstance(Context context) {
        if (mInstance == null)
            mInstance = new HttpUtil(context);
        return mInstance;
    }

    public static void releaseInstanceAPI() {
        if (mInstanceAPI != null) {
            mInstanceAPI = null;
        }
        if (mInstance != null) {
            mInstance = null;
        }
    }

    public HttpUtil(Context context, String apiLink) {
        configManager = new ConfigManager(context);
        final String token = configManager.getString(Constants.KEY_GET_TOKEN_API, "");
        Log.i("getToken_API", token);
        ConnectionSpec spec = new ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
                .tlsVersions(TlsVersion.TLS_1_2)
                .cipherSuites(
                        CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256,
                        CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256,
                        CipherSuite.TLS_DHE_RSA_WITH_AES_128_GCM_SHA256)
                .build();
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        Interceptor interceptorHeader = new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                Request request = original.newBuilder()
                        .header("User-Agent", "Chat")
                        .header("Accept", "application/vnd.chat.vn.v1.full+json")
                        .header("token", token)
                        .method(original.method(), original.body())
                        .build();
                return chain.proceed(request);
            }
        };
        TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[]{};
            }

            public void checkClientTrusted(X509Certificate[] chain,
                                           String authType) throws CertificateException {
            }

            public void checkServerTrusted(X509Certificate[] chain,
                                           String authType) throws CertificateException {
            }
        }};
        SSLContext sslContext = null;
        try {
            sslContext = SSLContext.getInstance("TLS");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        try {
            sslContext.init(null, trustAllCerts, null);
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        class RelaxedHostNameVerifier implements HostnameVerifier {
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        }

        OkHttpClient httpClient = null;
        if (Constants.API_SERVER_URL.startsWith("https://")) {
            if (Constants.DEVELOPER_MODE) {
                httpClient = new OkHttpClient.Builder()
                        .sslSocketFactory(sslContext.getSocketFactory())
                        .hostnameVerifier(new RelaxedHostNameVerifier())
                        .connectTimeout(30, TimeUnit.SECONDS)
                        .readTimeout(30, TimeUnit.SECONDS)
                        .writeTimeout(30, TimeUnit.SECONDS)
                        .addInterceptor(logging)
                        .addInterceptor(interceptorHeader)
                        .build();
            } else {
                httpClient = new OkHttpClient.Builder()
                        .sslSocketFactory(sslContext.getSocketFactory())
                        .hostnameVerifier(new RelaxedHostNameVerifier())
                        .connectTimeout(30, TimeUnit.SECONDS)
                        .readTimeout(30, TimeUnit.SECONDS)
                        .writeTimeout(30, TimeUnit.SECONDS)
                        .addInterceptor(interceptorHeader)
                        .build();
            }
        } else {
            if (Constants.DEVELOPER_MODE) {
                httpClient = new OkHttpClient.Builder()
                        .connectTimeout(30, TimeUnit.SECONDS)
                        .readTimeout(30, TimeUnit.SECONDS)
                        .writeTimeout(30, TimeUnit.SECONDS)
                        .addInterceptor(logging)
                        .addInterceptor(interceptorHeader)
                        .build();
            } else {
                httpClient = new OkHttpClient.Builder()
                        .connectTimeout(30, TimeUnit.SECONDS)
                        .readTimeout(30, TimeUnit.SECONDS)
                        .writeTimeout(30, TimeUnit.SECONDS)
                        .addInterceptor(interceptorHeader)
                        .build();
            }
        }
        retrofit = new Retrofit.Builder().client(httpClient).baseUrl(apiLink).addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        service = retrofit.create(APIService.class);
    }


    public Gson initGson() {
        Exclude ex = new Exclude();
        return new GsonBuilder()
                .disableHtmlEscaping()
                .registerTypeAdapter(Boolean.class, booleanAsIntAdapter)
                .registerTypeAdapter(boolean.class, booleanAsIntAdapter)
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .setPrettyPrinting()
                .serializeNulls()
                .setExclusionStrategies(new ExclusionStrategy() {
                    @Override
                    public boolean shouldSkipField(FieldAttributes f) {
                        return f.getDeclaringClass().equals(RealmObject.class);
                    }

                    @Override
                    public boolean shouldSkipClass(Class<?> clazz) {
                        return false;
                    }
                })
                .addDeserializationExclusionStrategy(ex).addSerializationExclusionStrategy(ex)
                .serializeNulls()
                .registerTypeHierarchyAdapter(byte[].class,
                        new ByteArrayToBase64TypeAdapter())
                .create();
    }



    @Override
    protected void showErrorMessageAndFinish(String error) {

    }

    @Override
    protected void showErrorMessageAndFinish(String error, Object object) {

    }

    class Exclude implements ExclusionStrategy {

        @Override
        public boolean shouldSkipClass(Class<?> arg0) {
            // TODO Auto-generated method stub
            return false;
        }

        @Override
        public boolean shouldSkipField(FieldAttributes field) {
            SerializedName ns = field.getAnnotation(SerializedName.class);
            if (ns != null)
                return false;
            return true;
        }
    }

    private TypeAdapter<Boolean> booleanAsIntAdapter = new TypeAdapter<Boolean>() {
        @Override
        public void write(JsonWriter out, Boolean value) throws IOException {
            if (value == null) {
                out.nullValue();
            } else {
                out.value(value);
            }
        }

        @Override
        public Boolean read(JsonReader in) throws IOException {
            JsonToken peek = in.peek();
            switch (peek) {
                case BOOLEAN:
                    return in.nextBoolean();
                case NULL:
                    in.nextNull();
                    return null;
                case NUMBER:
                    return in.nextInt() != 0;
                case STRING:
                    return Boolean.parseBoolean(in.nextString());
                default:
                    throw new IllegalStateException("Expected BOOLEAN or NUMBER but was " + peek);
            }
        }
    };
    // Using Android's base64 libraries. This can be replaced with any base64 library.
    private static class ByteArrayToBase64TypeAdapter implements JsonSerializer<byte[]>, JsonDeserializer<byte[]> {
        public byte[] deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            return Base64.decode(json.getAsString(), Base64.NO_WRAP);
        }

        public JsonElement serialize(byte[] src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(Base64.encodeToString(src, Base64.NO_WRAP));
        }
    }

    public HttpUtil(Context context) {
        configManager = new ConfigManager(context);
        Log.i("getToken_API", "HttpUtil: " + configManager.getString(Constants.KEY_GET_TOKEN_API, ""));
        ConnectionSpec spec = new ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
                .tlsVersions(TlsVersion.TLS_1_2)
                .cipherSuites(
                        CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256,
                        CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256,
                        CipherSuite.TLS_DHE_RSA_WITH_AES_128_GCM_SHA256)
                .build();
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        Interceptor interceptorHeader = new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                Request request = original.newBuilder()
                        .header("User-Agent", "Chat")
                        .header("Accept", "application/vnd.chat.vn.v1.full+json")
                        .header("token", configManager.getString(Constants.KEY_GET_TOKEN_API, ""))
                        .method(original.method(), original.body())
                        .build();
                return chain.proceed(request);
            }
        };
        TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[]{};
            }

            public void checkClientTrusted(X509Certificate[] chain,
                                           String authType) throws CertificateException {
            }

            public void checkServerTrusted(X509Certificate[] chain,
                                           String authType) throws CertificateException {
            }
        }};
        SSLContext sslContext = null;
        try {
            sslContext = SSLContext.getInstance("TLS");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        try {
            sslContext.init(null, trustAllCerts, null);
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        class RelaxedHostNameVerifier implements HostnameVerifier {
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        }
        OkHttpClient httpClient = null;
        if (Constants.API_SERVER_URL.startsWith("https://")) {
            if (Constants.DEVELOPER_MODE) {
                httpClient = new OkHttpClient.Builder()
                        .sslSocketFactory(sslContext.getSocketFactory())
                        .hostnameVerifier(new RelaxedHostNameVerifier())
                        .connectTimeout(30, TimeUnit.SECONDS)
                        .readTimeout(30, TimeUnit.SECONDS)
                        .writeTimeout(30, TimeUnit.SECONDS)
                        .addInterceptor(logging)
                        .addInterceptor(interceptorHeader)
                        .build();
            } else {
                httpClient = new OkHttpClient.Builder()
                        .sslSocketFactory(sslContext.getSocketFactory())
                        .hostnameVerifier(new RelaxedHostNameVerifier())
                        .connectTimeout(30, TimeUnit.SECONDS)
                        .readTimeout(30, TimeUnit.SECONDS)
                        .writeTimeout(30, TimeUnit.SECONDS)
                        .addInterceptor(interceptorHeader)
                        .build();
            }
        } else {
            if (Constants.DEVELOPER_MODE) {
                httpClient = new OkHttpClient.Builder()
                        .connectTimeout(30, TimeUnit.SECONDS)
                        .readTimeout(30, TimeUnit.SECONDS)
                        .writeTimeout(30, TimeUnit.SECONDS)
                        .addInterceptor(logging)
                        .addInterceptor(interceptorHeader).build();
            } else {
                httpClient = new OkHttpClient.Builder()
                        .connectTimeout(30, TimeUnit.SECONDS)
                        .readTimeout(30, TimeUnit.SECONDS)
                        .writeTimeout(30, TimeUnit.SECONDS)
                        .addInterceptor(interceptorHeader).build();
            }
        }
        retrofit = new Retrofit.Builder().client(httpClient).baseUrl(Constants.API_SERVER_URL).addConverterFactory(GsonConverterFactory.create(gson)).build();
        service = retrofit.create(APIService.class);
    }

    public HttpUtil(boolean isResponseString) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient httpClient = new OkHttpClient();
        httpClient.interceptors().add(interceptor);
        httpClient.interceptors().add(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                Request request = original.newBuilder()
                        .header("User-Agent", "Chat")
                        .header("Accept", "application/vnd.chat.vn.v1.full+json")
                        .method(original.method(), original.body())
                        .build();
                return chain.proceed(request);
            }
        });
        retrofit = new Retrofit.Builder().client(httpClient).baseUrl(Constants.API_SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create()).build();
        service = retrofit.create(APIService.class);
    }


    public void login(final String TAG, String language, String deviceId, String username, String password, String deviceToken, int sandboxMode, String deviceOS, final OnResponseSuccess<LoginResponse, String> onResponseSuccess) {
        Call<LoginResponse> call = service.login(language, deviceId, username, password, deviceToken, sandboxMode, deviceOS);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (onResponseSuccess != null && response.body() != null) {
                    onResponseSuccess.onResponseSuccess(TAG, response.body(), null);
                } else {
                    if (onResponseSuccess != null) {
                        try {
                            String errorBody = response.errorBody().string();
                            LoginResponse errorResponse = JsonConverter.getLoginResponse(errorBody);
                            onResponseSuccess.onResponseSuccess(TAG, errorResponse, null);
                        } catch (Exception e) {
                            e.printStackTrace();
                            onResponseSuccess.onResponseError(TAG, response.raw().message());
                        }
                    }
                }
                Log.i(TAG, "============>response:" + response.toString());
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                if (onResponseSuccess != null) {
                    onResponseSuccess.onResponseError(TAG, t.getMessage());
                }
                Log.i(TAG, "============>error:" + t.getMessage());
            }
        });
    }

    public void getAllTimeLine(final String TAG, String language, String deviceId, String accessToken, int page, final OnResponseSuccess<TimelineResponse, String> onResponseSuccess) {
        Call<TimelineResponse> call = service.getAllTimeLine(language, deviceId, accessToken, page);
        call.enqueue(new Callback<TimelineResponse>() {
            @Override
            public void onResponse(Call<TimelineResponse> call, Response<TimelineResponse> response) {
                if (onResponseSuccess != null && response.body() != null) {
                    onResponseSuccess.onResponseSuccess(TAG, response.body(), null);
                } else {
                    if (onResponseSuccess != null) {
                        try {
                            String errorBody = response.errorBody().string();
                            TimelineResponse errorResponse = JsonConverter.getTimelineResponse(errorBody);
                            onResponseSuccess.onResponseSuccess(TAG, errorResponse, null);
                        } catch (Exception e) {
                            e.printStackTrace();
                            onResponseSuccess.onResponseError(TAG, response.raw().message());
                        }
                    }
                }
                Log.i(TAG, "============>response:" + response.toString());
            }

            @Override
            public void onFailure(Call<TimelineResponse> call, Throwable t) {
                if (onResponseSuccess != null) {
                    onResponseSuccess.onResponseError(TAG, t.getMessage());
                }
                Log.i(TAG, "============>error:" + t.getMessage());
            }
        });
    }

    public void logout(final String TAG, String language, String deviceId, String accessToken, final OnResponseSuccess<LoginResponse, String> onResponseSuccess) {
        Call<LoginResponse> call = service.logout(language, deviceId, accessToken);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (onResponseSuccess != null && response.body() != null) {
                    onResponseSuccess.onResponseSuccess(TAG, response.body(), null);
                } else {
                    if (onResponseSuccess != null) {
                        try {
                            String errorBody = response.errorBody().string();
                            LoginResponse errorResponse = JsonConverter.getLoginResponse(errorBody);
                            onResponseSuccess.onResponseSuccess(TAG, errorResponse, null);
                        } catch (Exception e) {
                            e.printStackTrace();
                            onResponseSuccess.onResponseError(TAG, response.raw().message());
                        }
                    }
                }
                Log.i(TAG, "============>response:" + response.toString());
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                if (onResponseSuccess != null) {
                    onResponseSuccess.onResponseError(TAG, t.getMessage());
                }
                Log.i(TAG, "============>error:" + t.getMessage());
            }
        });
    }

    public void requestListMessage(final String TAG,String lang,String device,String token, String room_id, String page, String message_id, final OnResponseSuccess<ListMessageInfo, String> onResponseSuccess) {

        Call<ListMessageInfo> call = service.requestListMessage(lang, device,token, room_id, page, message_id);
        call.enqueue(new Callback<ListMessageInfo>() {
            @Override
            public void onResponse(Call<ListMessageInfo> call, Response<ListMessageInfo> response) {
                if (onResponseSuccess != null && response.body() != null) {
                    onResponseSuccess.onResponseSuccess(TAG, response.body(), null);
                } else {
                    if (onResponseSuccess != null) {
                        try {
                            String errorBody = response.errorBody().string();
                            LoginResponse errorResponse = JsonConverter.getLoginResponse(errorBody);
                            onResponseSuccess.onResponseError(TAG, errorResponse.getMessage());
                        } catch (Exception e) {
                            e.printStackTrace();
                            onResponseSuccess.onResponseError(TAG, response.raw().message());
                        }
                    }
                }
                Log.i(TAG, "============>response:" + response.toString());
            }

            @Override
            public void onFailure(Call<ListMessageInfo> call, Throwable t) {
                if (onResponseSuccess != null) {
                    onResponseSuccess.onResponseError(TAG, t.getMessage());
                }
                Log.i(TAG, "============>error:" + t.getMessage());
            }
        });
    }

    public void seenMessenger(final String TAG,String lang,String device,String token, String messsege_id,final OnResponseSuccess<Message, String> onResponseSuccess) {

        Call<Message> call = service.seenMessenger(lang, device,token, messsege_id);
        call.enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
                if (onResponseSuccess != null && response.body() != null) {
                    onResponseSuccess.onResponseSuccess(TAG, response.body(), null);
                } else {
                    if (onResponseSuccess != null) {
                        try {
                            String errorBody = response.errorBody().string();
                            LoginResponse errorResponse = JsonConverter.getLoginResponse(errorBody);
                            onResponseSuccess.onResponseError(TAG, errorResponse.getMessage());
                        } catch (Exception e) {
                            e.printStackTrace();
                            onResponseSuccess.onResponseError(TAG, response.raw().message());
                        }
                    }
                }
                Log.i(TAG, "============>response:" + response.toString());
            }

            @Override
            public void onFailure(Call<Message> call, Throwable t) {
                if (onResponseSuccess != null) {
                    onResponseSuccess.onResponseError(TAG, t.getMessage());
                }
                Log.i(TAG, "============>error:" + t.getMessage());
            }
        });
    }

    public APIService getService() {
        return service;
    }
}


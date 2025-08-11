import com.google.gson.annotations.SerializedName;

public record Conversao(
        String result,
        @SerializedName("base_code") String baseCode,
        @SerializedName("target_code") String targetCode,
        @SerializedName("conversion_rate") double conversionRate,
        @SerializedName("conversion_result") double conversionResult,
        @SerializedName("time_last_update_utc") String atualizadoUtc,
        @SerializedName("error-type") String errorType
) {}
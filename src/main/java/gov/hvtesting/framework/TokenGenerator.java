package gov.hvtesting.framework;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import gov.hvtesting.utils.DateUtil;

public class TokenGenerator {

    private static final Long FAR_EXPIRATION_DATE = 1908187200l;

      public String getToken(Boolean isAvailable, String atf, Boolean isExpired)  {

        Long issuedAt = System.currentTimeMillis() / 1000l;

        Long expirationDateEnoch = FAR_EXPIRATION_DATE;
        if(isExpired) {
          expirationDateEnoch = LocalDate.now().minusDays(1).toEpochSecond(LocalTime.now(), ZoneOffset.UTC);
        }
        String secret = PropertyManager.getInstance(true).getSecret();
        String token = JWT.create()
            .withClaim("startDate", getStartDateEpoch())
            .withClaim("endDate", getEndDateEpoch())
            .withClaim("isAvailable", isAvailable)
            .withClaim("iat", issuedAt)
            .withClaim("exp", expirationDateEnoch)
            .withClaim("iss", "https://book-hgv-bus-trailer-mot.service.gov.uk")
            .withClaim("sub", atf)
            .sign(Algorithm.HMAC256(secret));

        return token;
    }

    private Long getStartDateEpoch(){
      DateUtil dateUtil = new DateUtil();
      LocalDate lastMonday = dateUtil.getLastMonday();
      return lastMonday.toEpochSecond(LocalTime.of(8,0), ZoneOffset.UTC);
    }

  private Long getEndDateEpoch(){
    DateUtil dateUtil = new DateUtil();
    LocalDate lastMonday = dateUtil.getLastMonday();
    LocalDate endDate = lastMonday.plusDays(27);
    return endDate.toEpochSecond(LocalTime.of(17,0), ZoneOffset.UTC);
  }
}

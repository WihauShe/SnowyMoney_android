package cn.cslg.alipay;

public class AlipayConfig {
    // 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
    public static String app_id = "2016102200739800";

    // 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQDSDcMPjA2RhgE7eQN1flJNSuGs6bEjT2O1196SsLfO/ALq6SJcOFVfCUNtXqGsrbXhnLLCJzhhP0ZszjhavPN4CNXJGVQ796vf0WQeSEXCSQmQOHOHFvJwFWsszKLxaFwtk5hJGGz9MWOIq98Rd1jb1fJ6D2Pm1gIDGPWk0X5x+BCqm6lx8GlTPiJYnDianA1+NFmA9UlWNQCRo23VSnN46o2fYr8ihwhxO2f0RVIwGFiUeqhFlFPsVAYOm0ln1AUB85Ih1PE1D8R7jHr/AcFbZKj4j2siQpjbmqY231DL7HhbWtv1c9uMNeXASdDd/7FDHhGc+2j8k0hQqRV+FD1hAgMBAAECggEAL8JrSHyqlRzn6NLSA4PuSJ6vAza7TPD/Fdh4RdgiYtCQBUNuyZMRbiNYDmUG05FHmn2bc2OhgRL2+0REMfVmUjOIEEpPCbd9f81HO09pdQxQPVdohR2lj/B+xtfccJEu7Dnz1SLVnRf+rpONi5YcQRe7Ix+eslyUZonlSE1Nt7GqhG6JFlJI2sPhb6GNJMgcvXzk8O08QDAyKry1IJ1ZGUFWTBOoX9ZFapcrE7T0iFZAP0+D9iBsv2m6T66Ym0YrmH2ZIENpJb6Ofb9R1fAgJfbP5yGp4NsBwdT8RE/FFRi+wzcsdMeI/lfQM6NeZHuKk6jAv2qie0k4S3cHEBADwQKBgQD+3doqd4XmX9q/87wtNpJbn/BtcBZa1N2x++KzbJVgtg/MkwM3aE7KLB1LDDeTWGBrmBiSwfdgwFD52qSeMibE3gEAswDeE7arYVxbx5ne5SCL2aZuTa+/CrtnASft9kx8y/AhjeqwNyhHdfbNsOBUVsICD+AVt5LtiHr2nj/SGQKBgQDS/OS5L8nBNdzGcR/w5LZLOWlrN/PRzqkp8OmRRkDNuKjEkCmCIqnQ9KdEWqB+TzE5W9bwJ1UWfzTtFTZ83DCPWKcRftS82W/6U91bCG+qa0nn3RD/8r5osCGcnNj7xgurbuEEueWrq1oYNZzM2wDDqBSVsK4Yoqetqfjadjj+iQKBgQCMr0nOR0vSbvcBHjmxe34utYqx3j14xifaJ4BsKnQKfnUaJ8yOuTVExDUnSAclfpM60So/WHKFJaONR/O2n1hnM2S6ThUcgSVEVOn2f6egMjpU+D+2g4uPmQz5PtobsOlnuetDmmMCAnBpgeRKhmhOJdXR7ZF0a3DfTYcSuyffgQKBgQDQrsu3ktm13YV5TWivRjg73RQ0tUnWidZtolgSlW8BSxS6HnC/w3M9Ns6+X/kAFWn1dq34KJ8TOkxp9qJHCy6IicHtHwpho5sK+KonIQf5gla+G7x1u22zdAo6sjQ3iqH+NRQhLZFJo5jf+x8Uy8RMwVFi4TbBLRg2P7jgszAaKQKBgQCWyCr1XtId2fDLnKR4B0fWCzoac8zHdY/VLbfpom4T7pwXM9UYkj4IL32e8vRLtHCY/zjt2pbGDgzbv8Ml2VlwtCnao8DkTK5FgZ9+ivudc+JO3YNrn/wzqgLUmhleGQ6S+Fu0qbWE4/F0JwhfcVK+pTDdJJjPRnnHE2SFUXSlHQ==";

    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA31m4/NFjTf27kIc8LHRdn78iF/Q8XQblfxhfiRztldQBali4pv07S+hKFdJV0jpI3RxSzXchL+ybqOQwTP8qdOnutHdODzuSWQ4NNGZzkcFL6nLMhgAuTEe5Vv/+tIlZ+0qxVJ+Y+EC+/MsmqGJIsHrKR9u98uP0CDWJ1bbSZO5PT8UDsw6pEY0KnuyHYCSeNJikTht3WQm+s1SEw/TXSkv5MrUxa67QOjxFCgN5IvjxnyQddP4DPx0+ZTLPIwQlddWI0XTv2/JK0BPujnXcNRs3YVfFjz6SsEqX8YM5o8zOiGjxJyOGrkDm0/MQ+mb3rpQsklsPCG1DlLF9HYlphQIDAQAB";

    // 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String notify_url = "http://127.0.0.1:8080/payresult";

    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    //public static String return_url = "http://localhost:8080/IGeekShop/ftl/paySuccess";

    // 签名方式
    public static String sign_type = "RSA2";

    // 字符编码格式
    public static String charset = "utf-8";

    // 支付宝网关
    public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";

    // 日子路径
    public static String log_path = "C:\\";
}

import java.math.BigInteger;
import java.net.NetworkInterface;
import java.security.MessageDigest;
import java.util.Enumeration;


public class hwid {
    public static String getHWID() {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(System.getProperty("os.name"));
            sb.append(System.getProperty("os.arch"));
            sb.append(System.getProperty("os.version"));
            sb.append(Runtime.getRuntime().availableProcessors());
            sb.append(System.getProperty("user.name"));

            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface ni = networkInterfaces.nextElement();
                byte[] mac = ni.getHardwareAddress();
                if (mac != null) {
                    for (byte b : mac) {
                        sb.append(String.format("%02X", b));
                    }
                }
            }

            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(sb.toString().getBytes());
            String hwid = new BigInteger(1, md.digest()).toString(16);
            System.out.println("Generated HWID:" + hwid);
            return hwid;
        } catch (Exception e) {
            System.out.println("Failed to generate HWID" + e);
            return "ERROR_GENERATING_HWID";
        }
    }
}

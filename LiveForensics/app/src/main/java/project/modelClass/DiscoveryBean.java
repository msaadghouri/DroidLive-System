package project.modelClass;

import java.io.Serializable;
import java.sql.Date;

/**
 * @author msaadghouri
 */
public class DiscoveryBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private String discoverId;
    private String userRefId;
    private String system;
    private String node;
    private String release;
    private String version;
    private String machine;
    private String kernel;
    private String fqdn;
    private String installDate;
    private String clientName;
    private String clientVersion;
    private String clientDescription;
    private Date buildTime;
    private String macAddress;
    private String ipv4;
    private String ipv6;
    private Date createdDate;
    private int transactionId;

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public String getDiscoverId() {
        return discoverId;
    }

    public void setDiscoverId(String discoverId) {
        this.discoverId = discoverId;
    }

    public String getUserRefId() {
        return userRefId;
    }

    public void setUserRefId(String userRefId) {
        this.userRefId = userRefId;
    }

    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    public String getNode() {
        return node;
    }

    public void setNode(String node) {
        this.node = node;
    }

    public String getRelease() {
        return release;
    }

    public void setRelease(String release) {
        this.release = release;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getMachine() {
        return machine;
    }

    public void setMachine(String machine) {
        this.machine = machine;
    }

    public String getKernel() {
        return kernel;
    }

    public void setKernel(String kernel) {
        this.kernel = kernel;
    }

    public String getFQDN() {
        return fqdn;
    }

    public void setFQDN(String fqdn) {
        this.fqdn = fqdn;
    }

    public String getInstallDate() {
        return installDate;
    }

    public void setInstallDate(String installDate) {
        this.installDate = installDate;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientVersion() {
        return clientVersion;
    }

    public void setClientVersion(String clientVersion) {
        this.clientVersion = clientVersion;
    }

    public String getClientDescription() {
        return clientDescription;
    }

    public void setClientDescription(String clientDescription) {
        this.clientDescription = clientDescription;
    }

    public Date getBuildTime() {
        return buildTime;
    }

    public void setBuildTime(Date buildTime) {
        this.buildTime = buildTime;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public String getIpv4() {
        return ipv4;
    }

    public void setIpv4(String ipv4) {
        this.ipv4 = ipv4;
    }

    public String getIpv6() {
        return ipv6;
    }

    public void setIpv6(String ipv6) {
        this.ipv6 = ipv6;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public DiscoveryBean(String userRefId, String system, String node, String release, String version, String machine, String kernel, String fqdn, String installDate, String clientName, String clientVersion, String clientDescription, Date buildTime, String macAddress, String ipv4, String ipv6, Date createdDate, int transactionId) {
        this.userRefId = userRefId;
        this.system = system;
        this.node = node;
        this.release = release;
        this.version = version;
        this.machine = machine;
        this.kernel = kernel;
        this.fqdn = fqdn;
        this.installDate = installDate;
        this.clientName = clientName;
        this.clientVersion = clientVersion;
        this.clientDescription = clientDescription;
        this.buildTime = buildTime;
        this.macAddress = macAddress;
        this.ipv4 = ipv4;
        this.ipv6 = ipv6;
        this.createdDate = createdDate;
        this.transactionId = transactionId;
    }

    @Override
    public String toString() {
        return "{\"discoverId\":" + "\"" + discoverId
                + "\"" + ", \"userRefId\":" + "\"" + userRefId
                + "\"" + ", \"system\":" + "\"" + system
                + "\"" + ", \"node\":" + "\"" + node
                + "\"" + ", \"release\":" + "\"" + release
                + "\"" + ", \"version\":" + "\"" + version
                + "\"" + ", \"machine\":" + "\"" + machine
                + "\"" + ", \"kernel\":" + "\"" + kernel
                + "\"" + ", \"fqdn\":" + "\"" + fqdn
                + "\"" + ", \"installDate\":" + "\"" + installDate
                + "\"" + ", \"clientName\":" + "\"" + clientName
                + "\"" + ", \"clientVersion\":" + "\"" + clientVersion
                + "\"" + ", \"clientDescription\":" + "\"" + clientDescription
                + "\"" + ", \"buildTime\":" + "\"" + buildTime
                + "\"" + ", \"macAddress\":" + "\"" + macAddress
                + "\"" + ", \"ipv4\":" + "\"" + ipv4
                + "\"" + ", \"ipv6\":" + "\"" + ipv6
                + "\"" + ", \"createdDate\":" + "\"" + createdDate
                + "\"" + ", \"transactionId\":" + "\"" + transactionId
                + "\"" + "}";
    }
}

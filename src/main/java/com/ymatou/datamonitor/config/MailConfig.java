package com.ymatou.datamonitor.config;

import com.baidu.disconf.client.common.annotations.DisconfFile;
import com.baidu.disconf.client.common.annotations.DisconfFileItem;
import org.springframework.stereotype.Component;

@Component
@DisconfFile(fileName = "mail.properties")
public class MailConfig {

    private String emailHost;
    private String emailPassword;
    private String emailUser;
    private String emailPort;
    private String fromEmail;
    private String emailReceiver;
    private boolean emailMonitorOn;

    @DisconfFileItem(name = "email.emailHost")
    public String getEmailHost() {
        return emailHost;
    }

    public void setEmailHost(String emailHost) {
        this.emailHost = emailHost;
    }

    @DisconfFileItem(name = "email.emailPassword")
    public String getEmailPassword() {
        return emailPassword;
    }

    public void setEmailPassword(String emailPassword) {
        this.emailPassword = emailPassword;
    }

    @DisconfFileItem(name = "email.emailUser")
    public String getEmailUser() {
        return emailUser;
    }

    public void setEmailUser(String emailUser) {
        this.emailUser = emailUser;
    }

    @DisconfFileItem(name = "email.emailPort")
    public String getEmailPort() {
        return emailPort;
    }

    public void setEmailPort(String emailPort) {
        this.emailPort = emailPort;
    }

    @DisconfFileItem(name = "email.fromEmail")
    public String getFromEmail() {
        return fromEmail;
    }

    public void setFromEmail(String fromEmail) {
        this.fromEmail = fromEmail;
    }

    @DisconfFileItem(name = "email.emailMonitorOn")
    public boolean isEmailMonitorOn() {
        return emailMonitorOn;
    }

    public void setEmailMonitorOn(boolean emailMonitorOn) {
        this.emailMonitorOn = emailMonitorOn;
    }

    public String getEmailReceiver() {
        return emailReceiver;
    }

    public void setEmailReceiver(String emailReceiver) {
        this.emailReceiver = emailReceiver;
    }

    @Override
    public String toString() {
        return "ApplicationPropertyConfig [emailHost=" + emailHost + ", emailPassword=" + emailPassword +
                   ", emailUser=" + emailUser + ", emailPort=" + emailPort + ", fromEmail=" + fromEmail +
                   ", emailReceiver=" + emailReceiver + ", emailMonitorOn=" + emailMonitorOn + "]";
    }

}

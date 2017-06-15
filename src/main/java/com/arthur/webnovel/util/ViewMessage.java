package com.arthur.webnovel.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public class ViewMessage {
    public static Builder success() {
        return new Builder(Type.SUCCESS);
    }

    public static Builder info() {
        return new Builder(Type.INFO);
    }

    public static Builder error() {
        return new Builder(Type.ERROR);
    }

    private final Type type;
    private final String title;
    private final String message;

    public ViewMessage(Type type, String title, String message) {
        this.type = type;
        this.title = title;
        this.message = message;
    }

    public Type getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    public String getNotifyCode() {
    	StringBuilder sb = new StringBuilder();
        sb.append("<script>require(['ui/notify'], function(Notify) {");
        sb.append("var notify = new Notify();");
        sb.append("notify.show({");

        sb.append("message: '"+message+"',");

        switch (type) {
	        case SUCCESS:
	            sb.append("type: 'success',");
	            sb.append("icon: 'choice-bold'");
	            break;
	        case INFO:
	        	//sb.append("type: 'warning',");
	            sb.append("icon: 'menu'");
	            break;
	        case ERROR:
	            sb.append("type: 'warning',");
	            sb.append("icon: 'warning'");
	            break;
	        default:
	        	sb.append("icon: 'menu'");
	    }
        sb.append("});");
        sb.append("});</script>");
        return sb.toString();
    }

    public String getAlertCode() {
    	StringBuilder sb = new StringBuilder();
        sb.append("<script>alert");
        sb.append("('").append(message).append("'");
        sb.append(")</script>");
        return sb.toString();
    }

    public String getToastrCode() {
        StringBuilder sb = new StringBuilder();
        sb.append("<script>toastr.");
        switch (type) {
            case SUCCESS:
                sb.append("success");
                break;
            case INFO:
                sb.append("info");
                break;
            case ERROR:
                sb.append("error");
                break;
            default:
                sb.append("info");
        }
        sb.append("('").append(message).append("'");
        if (StringUtils.isNotBlank(title)) {
            sb.append(",'").append(title).append("'");
        }
        sb.append(")</script>");
        return sb.toString();
    }

    public static enum Type {
        SUCCESS, INFO, ERROR;
    }

    public static class Builder {
        private Type type;
        private String title;
        private String message;

        private Builder(Type type) {
            this.type = type;
        }

        public Builder title(String t) {
            this.title = t;
            return this;
        }

        public Builder message(String m) {
            this.message = m;
            return this;
        }

        public ViewMessage build() {
            return new ViewMessage(type, title, message);
        }

        public void register(RedirectAttributes attrs) {
            ViewMessage vmsg = build();
            attrs.addFlashAttribute(Const.VIEW_MESSAGE_FLASH_KEY, vmsg);
        }
    }
}
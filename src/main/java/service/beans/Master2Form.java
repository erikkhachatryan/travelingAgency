package service.beans;

public class Master2Form {
    private Master1Form master1Form;

    private String systemName;

    public Master2Form(String systemName) {
        this.systemName = systemName;
    }

    public Master1Form getMaster1Form() {
        return master1Form;
    }

    public void setMaster1Form(Master1Form master1Form) {
        this.master1Form = master1Form;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }
}

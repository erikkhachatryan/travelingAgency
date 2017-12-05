package service.beans;

public class Master2Form {
    private TravelingLocationForm travelingLocationForm;

    private String systemName;

    public Master2Form(String systemName) {
        this.systemName = systemName;
    }

    public TravelingLocationForm getTravelingLocationForm() {
        return travelingLocationForm;
    }

    public void setTravelingLocationForm(TravelingLocationForm travelingLocationForm) {
        this.travelingLocationForm = travelingLocationForm;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }
}

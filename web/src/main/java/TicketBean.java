

public class TicketBean {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOperationResult() {
        return "train " + name + " buy ticket operation result";
    }

}
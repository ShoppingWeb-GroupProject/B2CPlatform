package model;

public class MemberPrivilege {
    private String id;
    private String name;
    private boolean isBlacklisted;

    public MemberPrivilege(String id, String name) {
        this.id = id;
        this.name = name;
        this.isBlacklisted = false;
    }

    // Getter / Setter
    public String getId() { return id; }
    public String getName() { return name; }
    public boolean isBlacklisted() { return isBlacklisted; }

    public void setBlacklisted(boolean blacklisted) {
        isBlacklisted = blacklisted;
    }
}
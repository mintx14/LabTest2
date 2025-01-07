package my.utem.ftmk.labtest2.adapter;

public class BMIRecord {
    private String name;
    private float weight;
    private float height;
    private String status;

    public BMIRecord(String name, float weight, float height, String status) {
        this.name = name;
        this.weight = weight;
        this.height = height;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public float getWeight() {
        return weight;
    }

    public float getHeight() {
        return height;
    }

    public String getStatus() {
        return status;
    }
}


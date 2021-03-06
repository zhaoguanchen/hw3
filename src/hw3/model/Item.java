package hw3.model;

public class Item {
    private Integer itemId;
    private String name;
    private String unit;
    private String manufacturer;

    public Item(Integer itemId, String name, String unit, String manufacturer) {
        this.itemId = itemId;
        this.name = name;
        this.unit = unit;
        this.manufacturer = manufacturer;
    }

    public Item() {

    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    @Override
    public String toString() {
        return "Item{" +
                "itemId=" + itemId +
                ", name='" + name + '\'' +
                ", unit='" + unit + '\'' +
                ", manufacturer='" + manufacturer + '\'' +
                '}';
    }
}


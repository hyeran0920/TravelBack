package filminkorea.fik.dtos;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "response")
public class ResponseWrapper {

    @XmlElement(name = "body")
    private Body body;

    public List<FoodDto> getItems() {
        return body != null ? body.getItems() : null;
    }

    public static class Body {

        @XmlElement(name = "items")
        private Items items;

        public List<FoodDto> getItems() {
            return items != null ? items.getItemList() : null;
        }
    }

    public static class Items {

        @XmlElement(name = "item")
        private List<FoodDto> itemList;

        public List<FoodDto> getItemList() {
            return itemList;
        }
    }
}


package li.lazzarini.microservices_sample.micro;

import li.lazzarini.microservices_sample.entities.OrderPosition;
import li.lazzarini.microservices_sample.entities.Product;
import li.lazzarini.microservices_sample.mongo.AdapterFactory;
import li.lazzarini.microservices_sample.mongo.ProductAdapter;

import java.io.IOException;
import java.util.List;

public class ProductService {
    private final AdapterFactory adapterFactory;
    public ProductService() {
        this.adapterFactory = new AdapterFactory();
    }

    public List<Product> getAllProducts() throws IOException {
        ProductAdapter adapter = (ProductAdapter) this.adapterFactory.getAdapter(Product.class);
        return adapter.getAll();
    }

    public void bookProducts(List<OrderPosition> positions) throws IOException {
        for(OrderPosition position : positions) {
            this.bookProduct(position.getProduct(), position.getCount());
        }
    }

    public void bookProduct(Product product, int quantity) throws IOException {
        ProductAdapter adapter = (ProductAdapter) this.adapterFactory.getAdapter(Product.class);
        adapter.bookProduct(product, quantity);
    }
}

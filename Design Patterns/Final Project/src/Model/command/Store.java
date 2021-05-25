package Model.command;

import Model.Product;
import Model.comparators.StringAscComparator;
import Model.iterator.FileHandler;
import Model.memento.Memento;
import Model.observer.Customer;
import Model.observer.Notifier;
import java.util.*;

public class Store implements StoreCommand {
    public static final String PRODUCTS_TXT = "products.txt";
    public static final String DATA_PATH = "data/";

    public final static int ASC = 0;
    public final static int DESC = 1;
    public final static int DEFAULT = 2;

    private Map<String, Product> allProducts;
    private final FileHandler binaryFileManager;
    private int saveAndPrintAs;
    private Memento previous;
    private final ArrayList<String> names;

    private final Notifier notifier = Notifier.getInstance();

    public Store() {
        saveAndPrintAs = DEFAULT;
        binaryFileManager = new FileHandler(DATA_PATH + PRODUCTS_TXT);
        names = new ArrayList<>();
        loadFromFile();
    }

    @Override
    public boolean addProduct(Product newProduct) {
        previous = createMemento();

        String serialNumber = newProduct.getSku();
        boolean isReplaced = false;

        // True - replaced
        if (allProducts.containsKey(serialNumber)) {
            isReplaced = true;
            allProducts.replace(serialNumber, newProduct);
            binaryFileManager.replaceProductBySerialNumber(newProduct);
        } else {
            allProducts.put(newProduct.getSku(), newProduct);
            writeProducts();
        }

        return isReplaced;
    }

    @Override
    public boolean removeProduct(String sku) {
        boolean success = false;
        if (allProducts.containsKey(sku)) {
            String currName = allProducts.get(sku).getCustomer().getName();
            names.remove(currName);
            allProducts.remove(sku);
            success = removeProductFromFile(sku);
        }
        return success;
    }

    @Override
    public Product getProduct(String sku) {
        return allProducts.get(sku);
    }

    @Override
    public void setOrderBy(int orderBy) {
        if (saveAndPrintAs == DEFAULT) {
            saveAndPrintAs = orderBy;

            allProducts = createMap();
        }
    }

    @Override
    public ArrayList<String> getAllConfirmedCustomerNames() {
        // must be used after sendSaleMessage func
        return names;
    }

    @Override
    public void undo() {
        allProducts = previous.getProducts();
        writeAllFromMapToFile();
    }

    @Override
    public ArrayList<Product> getAllProducts() {
        return new ArrayList<>(allProducts.values());
    }

    @Override
    public void sendSaleMessage(String msg) {
        Customer customer;

        for(Product product : allProducts.values()){
            customer = product.getCustomer();
            if(customer.getEventOnSales()) {
                notifier.sendMSG(customer, msg);
                names.add(customer.getName());
            }
        }
    }

    @Override
    public boolean removeAllProduct() {
        ArrayList<Product> prodList = getAllProducts();
        for (Product product : prodList)
            if (!removeProduct(product.getSku()))
                return false;
        return true;
    }

    private boolean removeProductFromFile(String sku) {
        return binaryFileManager.removeProduct(sku);
    }

    private Map<String, Product> createMap() {
        // also used to clone the current Map
        Map<String, Product> currentState;

        switch (saveAndPrintAs) {
            case ASC:
            case DESC:
                currentState = new TreeMap<>(getComparatorByOrder(saveAndPrintAs));
                currentState.putAll(allProducts);
                break;
            default:
                currentState = new LinkedHashMap<>(allProducts);
                break;
        }

        return currentState;
    }

    private void writeProducts() {
        binaryFileManager.clearFile();
        for (Product product : allProducts.values())
            binaryFileManager.writeProduct(product);
    }

    private Comparator<String> getComparatorByOrder(int order) {
        StringAscComparator comparator = new StringAscComparator();
        if (order == ASC)
            return comparator;

        return comparator.reversed();
    }

    private void writeAllFromMapToFile() {
        binaryFileManager.clearFile();
        for (Product product : allProducts.values()) {
            binaryFileManager.writeProduct(product);
        }
    }

    private Memento createMemento() {
        return new Memento(createMap());
    }

    private void loadFromFile() {
        allProducts = binaryFileManager.readProducts();
    }

}

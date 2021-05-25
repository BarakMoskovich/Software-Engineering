package Model.iterator;

import Exceptions.AlertUserException;
import Model.Product;

import java.io.*;
import java.util.*;

public class FileHandler implements Iterable<Product> {

    private long readPos;
    private long fileSize;
    private RandomAccessFile raf;
    private String filePath;

    public static final String NULL_PRODUCT_ERROR = "End of iterator!\nCan't read more!";
    private static final String FILE_NOT_FOUND = "Can't open file in path: ";
    private static final String FILE_OPERATION_ERROR = "Can't operate on file in path: ";
    private static final String REMOVE_ERROR = "Can't remove Product that hasn't being read!";
    private static final String REMOVE_TWICE_ERROR = "Can't remove twice the same object!";
    private static final String REMOVE_EMPTY_FILE_ERROR = "Can't remove from an empty file!";
    private static final String END_OF_ITERATOR_ERROR = "You are in the end of the iterator!";
    private static final String PRODUCT_CLASS_NOT_FOUND_ERROR = "No class Product found!";

    public FileHandler(String fileName){
        filePath = fileName;

        try {
            raf = new RandomAccessFile(filePath, "rw");
            readPos = raf.getFilePointer();
            fileSize = raf.length();
        } catch (FileNotFoundException e) {
            new AlertUserException(FILE_NOT_FOUND + filePath).showErrorMessage();
        } catch (IOException e) {
            showIOExceptionMsg();
        }
    }

    public boolean removeProduct(String sku) {
        Iterator<Product> itr = iterator();
        boolean isRemoved = false;
        Product product;

        while(itr.hasNext() && !isRemoved) {
            product = itr.next();
            if(product == null){
                new AlertUserException(NULL_PRODUCT_ERROR).showErrorMessage();
                break;
            }
            if(sku.equals(product.getSku())) {
                itr.remove();
                isRemoved = true;
            }
        }
        return isRemoved;
    }

    public LinkedHashMap<String, Product> readProducts() {

        Iterator<Product> itr = iterator();
        LinkedHashMap<String, Product> result = new LinkedHashMap<>();
        Product temp;

        while(itr.hasNext()) {
            temp = itr.next();
            result.put(temp.getSku(), temp);
        }

        return result;
    }

    @Override
    public Iterator<Product> iterator() {
        return new BinaryFileIterator();
    }

    public void writeProduct(Product product){
        byte[] temp;
        try {
            temp = Product.serialize(product);

            int productByteLength = temp.length;

            raf.seek(fileSize);
            raf.writeInt(productByteLength);
            raf.write(temp);

            fileSize = raf.length(); // resize

        } catch (IOException e) {
            showIOExceptionMsg();
        }
    }

    public void clearFile() {
        try {
            raf.setLength(0);
            readPos = 0;
            fileSize = 0;
        } catch (IOException e) {
            showIOExceptionMsg();
        }
    }

    public void replaceProductBySerialNumber(Product product) {
        Iterator<Product> itr = iterator();
        String sku = product.getSku();
        Product fileProduct;
        long prev;
        long curr;
        readPos = 0;
        boolean isReplaced = false;

        while(itr.hasNext() && !isReplaced){
            try {
                prev = readPos;
                fileProduct = itr.next();
                if(fileProduct == null){
                    throw new AlertUserException(NULL_PRODUCT_ERROR);
                }
                if(sku.equals(fileProduct.getSku())) {
                    curr = raf.getFilePointer();

                    byte[] byteProd = Product.serialize(product);
                    int byteProdInt = byteProd.length;

                    byte[] restOfData = new byte[(int) (fileSize -
                            (curr)
                    )];

                    raf.read(restOfData); // read rest of data

                    raf.seek(prev); // move to prev

                    // write new prod
                    raf.writeInt(byteProdInt);
                    raf.write(byteProd);

                    // write rest of data
                    raf.write(restOfData);

                    fileSize = raf.getFilePointer();

                    isReplaced = true;
                }
            } catch (IOException e) {
                showIOExceptionMsg();
            } catch (AlertUserException e) {
                e.showErrorMessage();
            }
        }

    }

    private void showIOExceptionMsg() {
        new AlertUserException(FILE_OPERATION_ERROR + filePath).showErrorMessage();
    }

    private class BinaryFileIterator implements Iterator<Product> {
        long read = 0;
        long prevRead;

        @Override
        public boolean hasNext() {
            return read < fileSize;
        }

        @Override
        public void remove() {
            if(read == 0)
                new AlertUserException(REMOVE_ERROR).showErrorMessage();
            else if(prevRead == read)
                new AlertUserException(REMOVE_TWICE_ERROR).showErrorMessage();
            else if (fileSize < read)
                new AlertUserException(REMOVE_EMPTY_FILE_ERROR).showErrorMessage();
            else {
                try {
                    raf.seek(read);

                    // rest of file except for a product
                    byte[] data = new byte[(int) (fileSize -
                            (read)
                    )];

                    raf.read(data);
                    raf.setLength(prevRead);
                    raf.write(data);

                    read = prevRead;
                    readPos = read;
                    fileSize = raf.length();

                } catch (IOException e) {
                    showIOExceptionMsg();
                }
            }
        }

        @Override
        public Product next() {
            Product result = null;
            if(!hasNext()) {
                new AlertUserException(END_OF_ITERATOR_ERROR).showErrorMessage();
            }
            else{
                try {
                    prevRead = read;
                    raf.seek(read);
                    int byteSize = raf.readInt();
                    byte[] arr = new byte[byteSize];
                    raf.read(arr);
                    Product product = Product.deserialize(arr);
                    read = raf.getFilePointer();
                    readPos = read;

                    result = product;
                } catch (IOException e) {
                    showIOExceptionMsg();
                } catch (ClassNotFoundException e) {
                    new AlertUserException(PRODUCT_CLASS_NOT_FOUND_ERROR).showErrorMessage();
                }
            }

            return result;
        }
    }
}
package demo.wrappers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class Wrappers {
    /*
     * Write your selenium wrappers here
     */

    public static void navigateToUrl(WebDriver driver, String url) {
        try {
            driver.get(url);
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }

    }

    public static void searchProduct(WebDriver driver, String text) {
        try {
            // fetch the search box web element
            WebElement searchBox = driver.findElement(By.name("q"));
            // clear and send text to search
            searchBox.clear();
            searchBox.sendKeys(text);
            searchBox.sendKeys(Keys.ENTER);
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }

    }

    public static void clickElement(WebDriver driver, By locatedBy) {
        try {
            // scroll to the element before clicking
            JavascriptExecutor js = (JavascriptExecutor) driver;
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.presenceOfElementLocated(locatedBy));
            // locate the element to click
            WebElement elementToClick = driver.findElement(locatedBy);
            // use javascript to click on the element
            js.executeScript("arguments[0].click()", elementToClick);
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }
    }

    public static void getCountOfProductsByRating(WebDriver driver, By locatedBy, double rating) {
        // pass rating as parameter
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.presenceOfElementLocated(locatedBy));
            // get the list of all products rating
            List<WebElement> productRatingList = driver.findElements(locatedBy);
            // initialize count to zero, to count no. of products with the rating less than
            // or equal to given rating
            int count = 0;
            // loop through the rating list and compare
            for (WebElement productRating : productRatingList) {
                double currentRating = Double.parseDouble(productRating.getText());
                if (currentRating <= rating) {
                    count++;
                }
            }
            // print the total count
            System.out.println("Product count with rating <= " + rating + ": " + count);
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("exception: " + e.getMessage());
        }
    }

    /*
     * update this method to incorporate fetching title and product from the
     * discount element
     */
    public static void getListOfTitleAndDiscount(WebDriver driver, By locatedBy, int discount) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.presenceOfElementLocated(locatedBy));
            // get list of products with discount
            List<WebElement> allProductList = driver.findElements(locatedBy);

            // loop through the list of products, and filter discount greater than the given
            // discount value
            for (WebElement product : allProductList) {
                WebElement discountTxtElement = product.findElement(By.className("UkUFwK"));
                String discountTxt = discountTxtElement.getText();
                // fetching the integer part of the discount text using replace all
                int curr_discount = Integer.parseInt(discountTxt.replaceAll("[^\\d]", ""));

                if (curr_discount > discount) {
                    WebElement titleElement = product.findElement(By.className("KzDlHZ"));
                    System.out.println("Title: " + titleElement.getText() + ", Discount: " + curr_discount);
                }

            }
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("exception: " + e.getMessage());
        }
    }

    public static void getListOfProductWithHighReviews(WebDriver driver, By locatedBy) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.presenceOfElementLocated(locatedBy));
            List<WebElement> allProductList = driver.findElements(locatedBy);

            // to get img -> .//img[contains(@class,'DByuf')]
            // to get title -> .//a[@class='wjcEIp']
            // to get the number of reviews -> .//div[contains(@class,'afFzxY')]
            HashMap<Integer, Integer> productReviewListMap = new HashMap<>();
            int index = 0;
            for (WebElement product : allProductList) {
                // List<Integer> productReviewListItem = new ArrayList<>();
                WebElement reviewElement = product.findElement(By.xpath(".//span[contains(@class,'Wphh')]"));
                String reviewText = reviewElement.getText().replaceAll("[^\\d]", "");
                int reviewNumber = Integer.parseInt(reviewText);
                productReviewListMap.put(index++, reviewNumber);

            }

            // sort hasmap in descending order of reviews
            Map<Integer, Integer> productReviewListSortedMap = sortReviewList(productReviewListMap);
            // get index from sorted review map, fetch the product in allproductlist using
            // the index obtained, and
            // print title and img url
            System.out.println("Title and image URL of the 5 items with highest number of reviews:- ");
            for (Map.Entry<Integer, Integer> entry : productReviewListSortedMap.entrySet()) {
                // get product index from hashmap using get key
                int productIndex = entry.getKey();
                // fetch the product from allProductList using productIndex
                WebElement product = allProductList.get(productIndex);
                // using product webelement as parent, fetch Title and print
                WebElement productTitleElement = product.findElement(By.xpath(".//a[@class='wjcEIp']"));
                System.out
                        .println("Title: " + productTitleElement.getAttribute("title") + "(" + entry.getValue() + ")");
                // using product webelement as parent, fetch img and print src
                WebElement productImgElement = product.findElement(By.xpath(".//img[contains(@class,'DByuf')]"));
                System.out.println("Image url: " + productImgElement.getAttribute("src") + "\n");
            }

        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("exception: " + e.getMessage());
        }
    }

    public static Map<Integer, Integer> sortReviewList(HashMap<Integer, Integer> hm) {

        // Create a list from elements of HashMap
        List<Map.Entry<Integer, Integer>> list = new LinkedList<>(hm.entrySet());

        Collections.sort(list,
                Collections.reverseOrder((l1, l2) -> l1.getValue().compareTo(l2.getValue())));
        Map<Integer, Integer> sortedList = new LinkedHashMap<>();

        // return top 5 reviewed products
        for (Map.Entry<Integer, Integer> entry : list) {
            // System.err.println(l.get(0)+", "+l.get(1));
            sortedList.put(entry.getKey(), entry.getValue());
            if (sortedList.size() == 5) {
                break;
            }
        }

        return sortedList;
    }
}

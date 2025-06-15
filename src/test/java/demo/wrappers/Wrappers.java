package demo.wrappers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
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
            WebElement searchBox = driver.findElement(By.name("q"));
            searchBox.sendKeys(text);
            searchBox.sendKeys(Keys.ENTER);
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }

    }

    public static void clickElement(WebDriver driver, By locatedBy) {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.presenceOfElementLocated(locatedBy));
            WebElement elementToClick = driver.findElement(locatedBy);
            js.executeScript("arguments[0].click()", elementToClick);
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }
    }

    // can we create a method just to fetch all the products in a list and then use
    // this to get internal elements for different uses?
    public static void getCountOfProductsByRating(WebDriver driver, By locatedBy) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.presenceOfElementLocated(locatedBy));
            List<WebElement> productRatingList = driver.findElements(locatedBy);
            int count = 0;
            for (WebElement productRating : productRatingList) {
                double rating = Double.parseDouble(productRating.getText());
                if (rating <= 4.0) {
                    count++;
                }
            }
            System.out.println("Product count with rating <= 4: " + count);
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("exception: " + e.getMessage());
        }
    }

    // to print we could create a method that accepts List of List and prints
    // everything
    public static void getListOfTitleAndDiscount(WebDriver driver, By locatedBy) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.presenceOfElementLocated(locatedBy));
            List<WebElement> allProductList = driver.findElements(locatedBy);

            for (WebElement product : allProductList) {
                WebElement discountTxtElement = product.findElement(By.className("UkUFwK"));
                String discountTxt = discountTxtElement.getText();
                int endIndex = discountTxt.indexOf("%");
                int discount = Integer.parseInt(discountTxt.substring(0, endIndex));

                if (discount > 17) {
                    WebElement titleElement = product.findElement(By.className("KzDlHZ"));
                    System.out.println("Title: " + titleElement.getText() + ", Discount: " + discount);
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
            List<List<Integer>> productReviewList = new ArrayList<>();
            int index = 0;
            for (WebElement product : allProductList) {
                List<Integer> productReviewListItem = new ArrayList<>();
                WebElement reviewElement = product.findElement(By.xpath(".//span[contains(@class,'Wphh')]"));
                String reviewText = reviewElement.getText().replaceAll("[^\\d]", "");
                int reviewNumber = Integer.parseInt(reviewText);
                productReviewListItem.add(index++);
                productReviewListItem.add(reviewNumber);
                productReviewList.add(productReviewListItem);
            }

            // sort list in descending order of reviews
            List<List<Integer>> productReviewListSorted = sortReviewList(productReviewList);
            // get index from sorted review list, fetch the product in allproductlist, and
            // print title and img url
            System.out.println("Title and image URL of the 5 items with highest number of reviews:- ");
            for (List<Integer> l : productReviewListSorted) {
                int productIndex = l.get(0);
                WebElement product = allProductList.get(productIndex);
                WebElement productTitleElement = product.findElement(By.xpath(".//a[@class='wjcEIp']"));
                System.out.println("Title: " + productTitleElement.getAttribute("title"));
                WebElement productImgElement = product.findElement(By.xpath(".//img[contains(@class,'DByuf')]"));
                System.out.println("Image url: " + productImgElement.getAttribute("src")+"\n");
            }

        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("exception: " + e.getMessage());
        }
    }

    // creating a comparator class
    static class listDescendingComparator implements Comparator<List<Integer>> {
        public int compare(List<Integer> l1, List<Integer> l2) {
            if (l1.get(1) == l2.get(1)) {
                return 0;
            } else if (l1.get(1) < l2.get(1)) {
                return 1;
            } else {
                return -1;
            }
        }
    }

    public static List<List<Integer>> sortReviewList(List<List<Integer>> list) {

        Collections.sort(list, new listDescendingComparator());
        List<List<Integer>> sortedList = new ArrayList<>();

        // return top 5 reviewed products
        for (List<Integer> l : list) {
            sortedList.add(l);
            if (sortedList.size() == 5) {
                break;
            }
        }

        return sortedList;
    }
}

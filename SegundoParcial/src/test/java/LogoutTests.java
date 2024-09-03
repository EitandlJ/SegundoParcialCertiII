import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LogoutTests extends BaseTest {

    @Test
    public void productRemainsInCartAfterLogoutAndLoginTest() {
        // Login
        WebElement userNameTextBox = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.presenceOfElementLocated(By.id("user-name")));
        userNameTextBox.sendKeys("standard_user");

        WebElement passwordTextBox = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.presenceOfElementLocated(By.name("password")));
        passwordTextBox.sendKeys("secret_sauce");

        WebElement loginButton = driver.findElement(By.id("login-button"));
        loginButton.click();

        waitWithSleep();

        // Añadir el primer producto al carrito
        WebElement firstProductAddToCartButton = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".btn_primary.btn_inventory")));
        firstProductAddToCartButton.click();

        waitWithSleep();

        // Abrir el menú
        WebElement menuButton = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".bm-burger-button")));
        menuButton.click();

        waitWithSleep();

        // Seleccionar la opción de cerrar sesión
        WebElement logoutButton = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.presenceOfElementLocated(By.id("logout_sidebar_link")));
        logoutButton.click();

        waitWithSleep();

        // Iniciar sesión nuevamente
        userNameTextBox = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.presenceOfElementLocated(By.id("user-name")));
        userNameTextBox.sendKeys("standard_user");

        passwordTextBox = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.presenceOfElementLocated(By.name("password")));
        passwordTextBox.sendKeys("secret_sauce");

        loginButton = driver.findElement(By.id("login-button"));
        loginButton.click();

        waitWithSleep();

        // Ir al carrito de compras
        WebElement shoppingCartIcon = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.presenceOfElementLocated(By.className("shopping_cart_link")));
        shoppingCartIcon.click();

        waitWithSleep();

        // Verificar que el producto sigue en el carrito
        WebElement productInCart = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.presenceOfElementLocated(By.className("inventory_item_name")));

        String expectedProductName = "Sauce Labs Backpack";
        String actualProductName = productInCart.getText();

        Assertions.assertEquals(expectedProductName, actualProductName, "The product is not present in the cart after logging back in.");

        waitWithSleep();

        // Eliminar el producto del carrito
        WebElement removeButton = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".btn_secondary.cart_button")));
        removeButton.click();
    }
    private void waitWithSleep() {
        try {
            Thread.sleep(1000); // Esperar 3 segundos
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

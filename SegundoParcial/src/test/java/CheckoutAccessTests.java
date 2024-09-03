import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CheckoutAccessTests extends BaseTest {

    @Test
    public void accessCheckoutWithoutSelectingProductsTest() {
        // Login
        WebElement userNameTextBox = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.presenceOfElementLocated(By.id("user-name")));
        userNameTextBox.sendKeys("standard_user");

        WebElement passwordTextBox = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.presenceOfElementLocated(By.name("password")));
        passwordTextBox.sendKeys("secret_sauce");

        WebElement loginButton = driver.findElement(By.id("login-button"));
        loginButton.click();

        // Entrar al carrito de compras
        WebElement shoppingCartIcon = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.presenceOfElementLocated(By.className("shopping_cart_link")));
        shoppingCartIcon.click();

        // Click en el boton de Checkout
        WebElement checkoutButton = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".btn_action.checkout_button")));
        checkoutButton.click();

        // Esperar a que aparezca el subtitulo de Checkout: Your Information
        WebElement checkoutSubheader = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.className("subheader")));

        // Si se verifica que el subtitulo esta en pantalla significa que se pudo acceder al menu de Checkout sin haber seleccionado productos previamente
        String expectedSubheaderText = "Checkout: Your Information";
        String actualSubheaderText = checkoutSubheader.getText();
        Assertions.assertEquals(expectedSubheaderText, actualSubheaderText, "The checkout subheader is not as expected.");
        //Esto indicaria la presencia de un error en la pagina web al permitir realizar compras sin productos

    }
}

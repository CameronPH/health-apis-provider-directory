package gov.va.api.health.providerdirectory.service.controller.location;

import gov.va.api.health.providerdirectory.api.resources.Location;
import gov.va.api.health.providerdirectory.service.controller.Parameters;
import java.util.function.Supplier;
import org.junit.Test;
import org.springframework.util.MultiValueMap;

@SuppressWarnings("WeakerAccess")
public class LocationControllerTest {

  LocationController controller;

  @SuppressWarnings("unchecked")
  private void assertSearch(
      Supplier<Location.Bundle> invocation, MultiValueMap<String, String> params) {}

  @Test
  public void searchByAddressCity() {
    assertSearch(
        () -> controller.searchByAddressCity("me", 1, 10),
        Parameters.builder().add("identifier", "me").add("page", 1).add("_count", 10).build());
  }

  @Test
  public void searchByAddressPostalcode() {
    assertSearch(
        () -> controller.searchByAddressPostalcode("me", 1, 10),
        Parameters.builder().add("identifier", "me").add("page", 1).add("_count", 10).build());
  }

  @Test
  public void searchByAddressState() {
    assertSearch(
        () -> controller.searchByAddressState("me", 1, 10),
        Parameters.builder().add("identifier", "me").add("page", 1).add("_count", 10).build());
  }

  @Test
  public void searchById() {
    assertSearch(
        () -> controller.searchByIdentifier("me", 1, 10),
        Parameters.builder().add("identifier", "me").add("page", 1).add("_count", 10).build());
  }

  @Test
  public void searchByName() {
    assertSearch(
        () -> controller.searchByName("me", 1, 10),
        Parameters.builder().add("identifier", "me").add("page", 1).add("_count", 10).build());
  }
}

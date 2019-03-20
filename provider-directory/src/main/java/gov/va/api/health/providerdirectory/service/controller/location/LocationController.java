package gov.va.api.health.providerdirectory.service.controller.location;

import gov.va.api.health.providerdirectory.api.resources.Location;
import gov.va.api.health.providerdirectory.api.resources.OperationOutcome;
import gov.va.api.health.providerdirectory.service.controller.Bundler;
import gov.va.api.health.providerdirectory.service.controller.PageLinks;
import gov.va.api.health.providerdirectory.service.controller.Parameters;
import gov.va.api.health.providerdirectory.service.controller.Validator;
import java.util.Collections;
import java.util.function.Function;
import javax.validation.constraints.Min;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Request Mappings for Location Resource, see
 * http://www.fhir.org/guides/argonaut/pd/StructureDefinition-argo-location.html for implementation
 * details.
 */
@SuppressWarnings("WeakerAccess")
@Validated
@RestController
@RequestMapping(
  value = {"/api/Location"},
  produces = {"application/json"}
)
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class LocationController {

  private Transformer transformer;

  private Bundler bundler;

  private Location.Bundle bundle(MultiValueMap<String, String> parameters, int page, int count) {
    PageLinks.LinkConfig linkConfig =
        PageLinks.LinkConfig.builder()
            .path("Practitioner")
            .queryParams(parameters)
            .page(page)
            .recordsPerPage(count)
            .totalRecords(0)
            .build();
    return bundler.bundle(
        Bundler.BundleContext.of(
            linkConfig,
            Collections.emptyList(),
            transformer,
            Location.Entry::new,
            Location.Bundle::new));
  }

  /** Search by address-city. */
  @GetMapping(params = {"address-city"})
  public Location.Bundle searchByAddressCity(
      @RequestParam("address-city") String addressCity,
      @RequestParam(value = "page", defaultValue = "1") @Min(1) int page,
      @RequestParam(value = "_count", defaultValue = "1") @Min(0) int count) {
    return bundle(
        Parameters.builder()
            .add("address-city", addressCity)
            .add("page", page)
            .add("_count", count)
            .build(),
        page,
        count);
  }

  /** Search by address-postalcode. */
  @GetMapping(params = {"address-postalcode"})
  public Location.Bundle searchByAddressPostalcode(
      @RequestParam("address-postalcode") String addressPostalcode,
      @RequestParam(value = "page", defaultValue = "1") @Min(1) int page,
      @RequestParam(value = "_count", defaultValue = "1") @Min(0) int count) {
    return bundle(
        Parameters.builder()
            .add("address-postalcode", addressPostalcode)
            .add("page", page)
            .add("_count", count)
            .build(),
        page,
        count);
  }

  /** Search by address-state. */
  @GetMapping(params = {"address-state"})
  public Location.Bundle searchByAddressState(
      @RequestParam("address-state") String addressState,
      @RequestParam(value = "page", defaultValue = "1") @Min(1) int page,
      @RequestParam(value = "_count", defaultValue = "1") @Min(0) int count) {
    return bundle(
        Parameters.builder()
            .add("address-state", addressState)
            .add("page", page)
            .add("_count", count)
            .build(),
        page,
        count);
  }

  /** Search by identifier. */
  @GetMapping(params = {"identifier"})
  public Location.Bundle searchByIdentifier(
      @RequestParam("identifier") String identifier,
      @RequestParam(value = "page", defaultValue = "1") @Min(1) int page,
      @RequestParam(value = "_count", defaultValue = "1") @Min(0) int count) {
    return bundle(
        Parameters.builder()
            .add("identifier", identifier)
            .add("page", page)
            .add("_count", count)
            .build(),
        page,
        count);
  }

  /** Search by name. */
  @GetMapping(params = {"name"})
  public Location.Bundle searchByName(
      @RequestParam("name") String name,
      @RequestParam(value = "page", defaultValue = "1") @Min(1) int page,
      @RequestParam(value = "_count", defaultValue = "1") @Min(0) int count) {
    return bundle(
        Parameters.builder().add("name", name).add("page", page).add("_count", count).build(),
        page,
        count);
  }

  /** Hey, this is a validate endpoint. It validates. */
  @PostMapping(
    value = "/$validate",
    consumes = {"application/json", "application/json+fhir", "application/fhir+json"}
  )
  public OperationOutcome validate(@RequestBody Location.Bundle bundle) {
    return Validator.create().validate(bundle);
  }

  // TODO: Actually implement transformer
  public interface Transformer extends Function<Location, Location> {}
}

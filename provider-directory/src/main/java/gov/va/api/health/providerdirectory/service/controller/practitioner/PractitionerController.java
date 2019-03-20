package gov.va.api.health.providerdirectory.service.controller.practitioner;

import gov.va.api.health.providerdirectory.api.resources.OperationOutcome;
import gov.va.api.health.providerdirectory.api.resources.Practitioner;
import gov.va.api.health.providerdirectory.service.controller.Bundler;
import gov.va.api.health.providerdirectory.service.controller.Bundler.BundleContext;
import gov.va.api.health.providerdirectory.service.controller.PageLinks.LinkConfig;
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
 * http://www.fhir.org/guides/argonaut/pd/StructureDefinition-argo-practitioner.html for
 * implementation details.
 */
@SuppressWarnings("WeakerAccess")
@Validated
@RestController
@RequestMapping(
  value = {"/api/Practitioner"},
  produces = {"application/json"}
)
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class PractitionerController {

  private Transformer transformer;
  private Bundler bundler;

  // TODO: bundle function and search will be fairly different since ProviderDirectory calls out to
  // PPMS endpoint

  private Practitioner.Bundle bundle(
      MultiValueMap<String, String> parameters, int page, int count) {

    LinkConfig linkConfig =
        LinkConfig.builder()
            .path("Practitioner")
            .queryParams(parameters)
            .page(page)
            .recordsPerPage(count)
            .totalRecords(0)
            .build();
    return bundler.bundle(
        BundleContext.of(
            linkConfig,
            Collections.emptyList(),
            transformer,
            Practitioner.Entry::new,
            Practitioner.Bundle::new));
  }

  /** Search by identifier. */
  @GetMapping(params = {"identifier"})
  public Practitioner.Bundle searchByIdentifier(
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

  /** Search by family & given name. */
  @GetMapping(params = {"family", "given"})
  public Practitioner.Bundle searchByName(
      @RequestParam("family") String familyName,
      @RequestParam("given") String givenName,
      @RequestParam(value = "page", defaultValue = "1") @Min(1) int page,
      @RequestParam(value = "_count", defaultValue = "1") @Min(0) int count) {
    return bundle(
        Parameters.builder()
            .add("family", familyName)
            .add("given", givenName)
            .add("page", page)
            .add("_count", count)
            .build(),
        page,
        count);
  }

  /** Hey, this is a validate endpoint. It validates. */
  @PostMapping(
    value = "/$validate",
    consumes = {"application/json", "application/json+fhir", "application/fhir+json"}
  )
  public OperationOutcome validate(@RequestBody Practitioner.Bundle bundle) {
    return Validator.create().validate(bundle);
  }

  // TODO: Actually implement transformer
  public interface Transformer extends Function<Practitioner, Practitioner> {}
}

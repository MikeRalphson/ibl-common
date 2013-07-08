package bbc.iplayer.spring.mashery.iodocs;


import bbc.iplayer.spring.mashery.iodocs.annotations.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
@RequestMapping("/atoz")
public class SampleController {

    @Inject
    public SampleController() {
    }

    // TODO No debug as it needs to be in the abstract controller or similar
    @IoDocsResourceName("A to Z")
    @IoDocsMethodName("Programmes by initial title character")
    @IoDocsDescription("Get the Programmes whose title begins with the given initial character.")
    @RequestMapping(value = "/{character}/programmes", method = GET)
    @ResponseBody
    public String getByInitialCharacter(
            @IoDocsEnum({"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l",
                    "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "0-9"})
            @IoDocsDescription("The programme identifier.") @IoDocsDefaultString("a") @PathVariable("character") InitialCharacter character,
            @IoDocsEnum({"", "mobile", "tv", "web"})
            @IoDocsEnumDescriptions({"", "mobile", "television", "web"})
            @IoDocsDescription("The rights group to limit results to.") @RequestParam(value = "rights", required = true, defaultValue = "web") RightsGroup rights,
            @IoDocsDescription("The page index.") @RequestParam(value = "page", defaultValue = "1", required = false) Page page,
            @IoDocsDescription("The number of results to return.") @RequestParam(value = "per_page", defaultValue = "100", required = false) PageSize pageSize,
            @RequestParam(value = "initial_child_count", defaultValue = "4") InitialChildCount initialChildCount,
            @RequestParam(value = "test_required_param", defaultValue = "4", required = true) InitialChildCount testRequiredParam,
            @RequestParam(value = "test_boolean", required = false) boolean testBoolean,
            @IoDocsIgnore @RequestParam(value = "debug", required = false) boolean debug) throws Exception {

        return null;
    }

    @IoDocsResourceName("A to Z")
    @IoDocsIgnore
    @IoDocsMethodName("A to Z ignored method")
    @RequestMapping(value = "/{character}/programmes", method = GET)
    @ResponseBody
    public String ignoredMethod() throws Exception {
        return null;
    }

    @IoDocsResourceName("A to Z")
    @IoDocsMethodName("A to Z with no methods")
    @RequestMapping(value = "/{character}/programmes", method = GET)
    @ResponseBody
    public String noMethod() throws Exception {
        return null;
    }

    public void methodWithNoAnnotations() {

    }


    @IoDocsResourceName("Categories")
    @IoDocsMethodName("No request params")
    @IoDocsDescription("Get the list of all the categories in TV & iPlayer.")
    @RequestMapping(value = "/", method = GET)
    public
    @ResponseBody
    String noRequestParams() {

        return null;
    }

    @IoDocsResourceName("Categories")
    @IoDocsMethodName("No request mapping path")
    @IoDocsDescription("Get the list of all the categories in TV & iPlayer.")
    @RequestMapping(method = GET)
    public
    @ResponseBody
    String noRequestMappingPath() {

        return null;
    }

    @IoDocsResourceName("Categories")
    @IoDocsMethodName("With wrapping annotation")
    @IoDocsDescription("Get the list of all the categories in TV & iPlayer.")
    @RequestMapping(method = GET)
    public
    @ResponseBody
    String withAWrappingAnnotation(
            @WrappingAnnotation @RequestParam(value = "initial_child_count", defaultValue = "4") InitialChildCount initialChildCount) {

        return null;
    }
}

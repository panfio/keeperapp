package ru.panfio.keeper.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import ru.panfio.keeper.domain.Link;
import ru.panfio.keeper.repository.LinkRepo;

import javax.transaction.Transactional;
import java.util.Optional;

@Slf4j
@RestController
public class RedirectController {

    @Autowired
    private final LinkRepo linkRepo;

    public RedirectController(LinkRepo linkRepo) {
        this.linkRepo = linkRepo;
    }

    @Transactional
    @GetMapping("/to/{cut}")
    public ModelAndView redirectShortLink(@PathVariable String cut) {
        Optional<Link> maybeLink = linkRepo.findByCut(cut);
        if (maybeLink.isPresent()) {

            //todo concurrent access issues
            Link link = maybeLink.get();
            link.setVisitCount(link.getVisitCount() + 1);
            linkRepo.save(link);

            log.info("Redirected from /{}", cut);
            return new ModelAndView("redirect:" + link.getLink());
        }
        return new ModelAndView("redirect:/notfound");
    }

    //CHECKSTYLE:OFF
    @GetMapping("/notfound")
    public String notFoundPage() {
        return "<!DOCTYPE HTML>\n" +
                "<html xmlns:th=\"http://www.thymeleaf.org\">\n" +
                "<head>\n" +
                "    <title>Title</title>\n" +
                "    <style>\n" +
                "        @import url(\"https://fonts.googleapis.com/css?family=Fira+Sans\");\n" +
                "/*Variables */\n" +
                ".left-section .inner-content {\n" +
                "  position: absolute;\n" +
                "  top: 50%;\n" +
                "  transform: translateY(-50%);\n" +
                "}\n" +
                "\n" +
                "* {\n" +
                "  box-sizing: border-box;\n" +
                "}\n" +
                "\n" +
                "html, body {\n" +
                "  margin: 0;\n" +
                "  padding: 0;\n" +
                "}\n" +
                "\n" +
                "body {\n" +
                "  font-family: \"Fira Sans\", sans-serif;\n" +
                "  color: #f5f6fa;\n" +
                "}\n" +
                "\n" +
                ".background {\n" +
                "  position: absolute;\n" +
                "  top: 0;\n" +
                "  left: 0;\n" +
                "  width: 100%;\n" +
                "  height: 100%;\n" +
                "  background: linear-gradient(#0C0E10, #446182);\n" +
                "}\n" +
                ".background .ground {\n" +
                "  position: absolute;\n" +
                "  bottom: 0;\n" +
                "  width: 100%;\n" +
                "  height: 25vh;\n" +
                "  background: #0C0E10;\n" +
                "}\n" +
                "@media (max-width: 770px) {\n" +
                "  .background .ground {\n" +
                "    height: 0vh;\n" +
                "  }\n" +
                "}\n" +
                "\n" +
                ".container {\n" +
                "  position: relative;\n" +
                "  margin: 0 auto;\n" +
                "  width: 85%;\n" +
                "  height: 100vh;\n" +
                "  padding-bottom: 25vh;\n" +
                "  display: flex;\n" +
                "  flex-direction: row;\n" +
                "  justify-content: space-around;\n" +
                "}\n" +
                "@media (max-width: 770px) {\n" +
                "  .container {\n" +
                "    flex-direction: column;\n" +
                "    padding-bottom: 0vh;\n" +
                "  }\n" +
                "}\n" +
                "\n" +
                ".left-section, .right-section {\n" +
                "  position: relative;\n" +
                "}\n" +
                "\n" +
                ".left-section {\n" +
                "  width: 40%;\n" +
                "}\n" +
                "@media (max-width: 770px) {\n" +
                "  .left-section {\n" +
                "    width: 100%;\n" +
                "    height: 40%;\n" +
                "    position: absolute;\n" +
                "    top: 0;\n" +
                "  }\n" +
                "}\n" +
                "@media (max-width: 770px) {\n" +
                "  .left-section .inner-content {\n" +
                "    position: relative;\n" +
                "    padding: 1rem 0;\n" +
                "  }\n" +
                "}\n" +
                "\n" +
                ".heading {\n" +
                "  text-align: center;\n" +
                "  font-size: 9em;\n" +
                "  line-height: 1.3em;\n" +
                "  margin: 2rem 0 0.5rem 0;\n" +
                "  padding: 0;\n" +
                "  text-shadow: 0 0 1rem #fefefe;\n" +
                "}\n" +
                "@media (max-width: 770px) {\n" +
                "  .heading {\n" +
                "    font-size: 7em;\n" +
                "    line-height: 1.15;\n" +
                "    margin: 0;\n" +
                "  }\n" +
                "}\n" +
                "\n" +
                ".subheading {\n" +
                "  text-align: center;\n" +
                "  max-width: 480px;\n" +
                "  font-size: 1.5em;\n" +
                "  line-height: 1.15em;\n" +
                "  padding: 0 1rem;\n" +
                "  margin: 0 auto;\n" +
                "}\n" +
                "@media (max-width: 770px) {\n" +
                "  .subheading {\n" +
                "    font-size: 1.3em;\n" +
                "    line-height: 1.15;\n" +
                "    max-width: 100%;\n" +
                "  }\n" +
                "}\n" +
                "\n" +
                ".right-section {\n" +
                "  width: 50%;\n" +
                "}\n" +
                "@media (max-width: 770px) {\n" +
                "  .right-section {\n" +
                "    width: 100%;\n" +
                "    height: 60%;\n" +
                "    position: absolute;\n" +
                "    bottom: 0;\n" +
                "  }\n" +
                "}\n" +
                "\n" +
                ".svgimg {\n" +
                "  position: absolute;\n" +
                "  bottom: 0;\n" +
                "  padding-top: 10vh;\n" +
                "  padding-left: 1vh;\n" +
                "  max-width: 100%;\n" +
                "  max-height: 100%;\n" +
                "}\n" +
                "@media (max-width: 770px) {\n" +
                "  .svgimg {\n" +
                "    padding: 0;\n" +
                "  }\n" +
                "}\n" +
                ".svgimg .bench-legs {\n" +
                "  fill: #0C0E10;\n" +
                "}\n" +
                ".svgimg .top-bench, .svgimg .bottom-bench {\n" +
                "  stroke: #0C0E10;\n" +
                "  stroke-width: 1px;\n" +
                "  fill: #5B3E2B;\n" +
                "}\n" +
                ".svgimg .bottom-bench path:nth-child(1) {\n" +
                "  fill: #432d20;\n" +
                "}\n" +
                ".svgimg .lamp-details {\n" +
                "  fill: #202425;\n" +
                "}\n" +
                ".svgimg .lamp-accent {\n" +
                "  fill: #2c3133;\n" +
                "}\n" +
                ".svgimg .lamp-bottom {\n" +
                "  fill: linear-gradient(#202425, #0C0E10);\n" +
                "}\n" +
                ".svgimg .lamp-light {\n" +
                "  fill: #EFEFEF;\n" +
                "}\n" +
                "\n" +
                "@keyframes glow {\n" +
                "  0% {\n" +
                "    text-shadow: 0 0 1rem #fefefe;\n" +
                "  }\n" +
                "  50% {\n" +
                "    text-shadow: 0 0 1.85rem #ededed;\n" +
                "  }\n" +
                "  100% {\n" +
                "    text-shadow: 0 0 1rem #fefefe;\n" +
                "  }\n" +
                "}\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "\n" +
                "<div class=\"background\">\n" +
                "    <div class=\"ground\"></div>\n" +
                "</div>\n" +
                "<div class=\"container\">\n" +
                "    <div class=\"left-section\">\n" +
                "        <div class=\"inner-content\">\n" +
                "            <h1 class=\"heading\">404</h1>\n" +
                "            <p class=\"subheading\">Looks like the page you were looking for is no longer here.</p>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "    <div class=\"right-section\">\n" +
                "        <svg class=\"svgimg\" xmlns=\"http://www.w3.org/2000/svg\" viewBox=\"51.5 -15.288 385 505.565\">\n" +
                "            <g class=\"bench-legs\">\n" +
                "                <path d=\"M202.778,391.666h11.111v98.611h-11.111V391.666z M370.833,390.277h11.111v100h-11.111V390.277z M183.333,456.944h11.111\n" +
                "          v33.333h-11.111V456.944z M393.056,456.944h11.111v33.333h-11.111V456.944z\" />\n" +
                "            </g>\n" +
                "            <g class=\"top-bench\">\n" +
                "                <path d=\"M396.527,397.917c0,1.534-1.243,2.777-2.777,2.777H190.972c-1.534,0-2.778-1.243-2.778-2.777v-8.333\n" +
                "          c0-1.535,1.244-2.778,2.778-2.778H393.75c1.534,0,2.777,1.243,2.777,2.778V397.917z M400.694,414.583\n" +
                "          c0,1.534-1.243,2.778-2.777,2.778H188.194c-1.534,0-2.778-1.244-2.778-2.778v-8.333c0-1.534,1.244-2.777,2.778-2.777h209.723\n" +
                "          c1.534,0,2.777,1.243,2.777,2.777V414.583z M403.473,431.25c0,1.534-1.244,2.777-2.778,2.777H184.028\n" +
                "          c-1.534,0-2.778-1.243-2.778-2.777v-8.333c0-1.534,1.244-2.778,2.778-2.778h216.667c1.534,0,2.778,1.244,2.778,2.778V431.25z\"\n" +
                "                />\n" +
                "            </g>\n" +
                "            <g class=\"bottom-bench\">\n" +
                "                <path d=\"M417.361,459.027c0,0.769-1.244,1.39-2.778,1.39H170.139c-1.533,0-2.777-0.621-2.777-1.39v-4.86\n" +
                "          c0-0.769,1.244-0.694,2.777-0.694h244.444c1.534,0,2.778-0.074,2.778,0.694V459.027z\" />\n" +
                "                <path d=\"M185.417,443.75H400c0,0,18.143,9.721,17.361,10.417l-250-0.696C167.303,451.65,185.417,443.75,185.417,443.75z\" />\n" +
                "            </g>\n" +
                "            <g id=\"lamp\">\n" +
                "                <path class=\"lamp-details\" d=\"M125.694,421.997c0,1.257-0.73,3.697-1.633,3.697H113.44c-0.903,0-1.633-2.44-1.633-3.697V84.917\n" +
                "          c0-1.257,0.73-2.278,1.633-2.278h10.621c0.903,0,1.633,1.02,1.633,2.278V421.997z\"\n" +
                "                />\n" +
                "                <path class=\"lamp-accent\" d=\"M128.472,93.75c0,1.534-1.244,2.778-2.778,2.778h-13.889c-1.534,0-2.778-1.244-2.778-2.778V79.861\n" +
                "          c0-1.534,1.244-2.778,2.778-2.778h13.889c1.534,0,2.778,1.244,2.778,2.778V93.75z\" />\n" +
                "\n" +
                "                <circle class=\"lamp-light\" cx=\"119.676\" cy=\"44.22\" r=\"40.51\" />\n" +
                "                <path class=\"lamp-details\" d=\"M149.306,71.528c0,3.242-13.37,13.889-29.861,13.889S89.583,75.232,89.583,71.528c0-4.166,13.369-13.889,29.861-13.889\n" +
                "          S149.306,67.362,149.306,71.528z\"/>\n" +
                "                <radialGradient class=\"light-gradient\" id=\"SVGID_1_\" cx=\"119.676\" cy=\"44.22\" r=\"65\" gradientUnits=\"userSpaceOnUse\">\n" +
                "                    <stop  offset=\"0%\" style=\"stop-color:#FFFFFF; stop-opacity: 1\"/>\n" +
                "                    <stop  offset=\"50%\" style=\"stop-color:#EDEDED; stop-opacity: 0.5\">\n" +
                "                        <animate attributeName=\"stop-opacity\" values=\"0.0; 0.5; 0.0\" dur=\"5000ms\" repeatCount=\"indefinite\"></animate>\n" +
                "                    </stop>\n" +
                "                    <stop  offset=\"100%\" style=\"stop-color:#EDEDED; stop-opacity: 0\"/>\n" +
                "                </radialGradient>\n" +
                "                <circle class=\"lamp-light__glow\" fill=\"url(#SVGID_1_)\" cx=\"119.676\" cy=\"44.22\" r=\"65\"/>\n" +
                "                <path class=\"lamp-bottom\" d=\"M135.417,487.781c0,1.378-1.244,2.496-2.778,2.496H106.25c-1.534,0-2.778-1.118-2.778-2.496v-74.869\n" +
                "          c0-1.378,1.244-2.495,2.778-2.495h26.389c1.534,0,2.778,1.117,2.778,2.495V487.781z\" />\n" +
                "            </g>\n" +
                "        </svg>\n" +
                "    </div>\n" +
                "</div>\n" +
                "</body>\n" +
                "</html>";
        //CHECKSTYLE:ON
    }

}

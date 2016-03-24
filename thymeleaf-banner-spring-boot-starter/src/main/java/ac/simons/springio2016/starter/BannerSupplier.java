package ac.simons.springio2016.starter;

import java.util.List;
import org.thymeleaf.Arguments;
import org.thymeleaf.dom.Node;

@FunctionalInterface
interface BannerSupplier {

    List<Node> get(Arguments args);
}
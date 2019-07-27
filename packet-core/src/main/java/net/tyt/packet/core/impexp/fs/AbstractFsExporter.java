package net.tyt.packet.core.impexp.fs;

import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import net.tyt.packet.core.impexp.Exporter;
import org.eclipse.persistence.jaxb.MarshallerProperties;
import org.springframework.beans.factory.annotation.Value;
import net.tyt.packet.core.dto.Identifiable;

/**
 *
 * @author Игорь Тытарь
 * @param <T>
 */
public abstract class AbstractFsExporter<T extends Identifiable> extends FsSupport implements Exporter<T> {

    private String path;

    protected final Marshaller marshaller;

    public AbstractFsExporter() {
        try {
            marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty(MarshallerProperties.MEDIA_TYPE, "application/json");
            marshaller.setProperty(MarshallerProperties.JSON_INCLUDE_ROOT, false);
        } catch (JAXBException ex) {
            throw new RuntimeException("can't create marshaller", ex);
        }
    }
    @Override
    public void send(T item) {
        try {
            marshaller.marshal(item, getFile(item.getId()));
        } catch (Exception ex) {
            throw new IllegalArgumentException("can't export packet "+item,ex);
        }
    }

    @Override
    public String getPath() {
        return path;
    }

    @Value("${exchange.fs.out}")
    public void setPath(String path) {
        this.path = path;
    }

}

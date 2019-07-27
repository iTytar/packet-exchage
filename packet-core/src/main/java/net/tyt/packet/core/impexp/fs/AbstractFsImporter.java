package net.tyt.packet.core.impexp.fs;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import net.tyt.packet.core.impexp.Importer;
import org.eclipse.persistence.jaxb.UnmarshallerProperties;
import org.springframework.beans.factory.annotation.Value;

/**
 *
 * @author Игорь Тытарь
 * @param <T>
 */
public abstract class AbstractFsImporter<T> extends FsSupport implements Importer<T> {

    private String path;

    protected final Unmarshaller unmarshaller;

    public AbstractFsImporter() {
        try {
            unmarshaller = jaxbContext.createUnmarshaller();
            unmarshaller.setProperty(UnmarshallerProperties.MEDIA_TYPE, "application/json");
            unmarshaller.setProperty(UnmarshallerProperties.JSON_INCLUDE_ROOT, false);
        } catch (JAXBException ex) {
            throw new RuntimeException("can't create unmarshaller", ex);
        }
    }

    @Override
    public List<String> receivedIds() {
        return Arrays.asList(getDir().list());
    }

    @Override
    public T get(String id) {
        try {
            StreamSource ss = new StreamSource(getFile(id));
            return unmarshaller.unmarshal(ss,getItemClass()).getValue();
        } catch (JAXBException ex) {
            throw new IllegalArgumentException("can't get packet " + id, ex);
        }
    }

    @Override
    public void commit(String id) {
        File f = getFile(id);
        if (!f.delete()) {
            throw new IllegalArgumentException("can't delete file " + f);
        }
    }

    @Override
    public String getPath() {
        return path;
    }

    @Value("${exchange.fs.in}")
    public void setPath(String path) {
        this.path = path;
    }
    
    protected abstract Class<T> getItemClass();
}

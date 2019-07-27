package net.tyt.packet.core.impexp.fs;

import java.io.File;
import java.nio.file.Path;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import net.tyt.packet.core.dto.PacketDTO;
import net.tyt.packet.core.dto.StateDTO;

/**
 *
 * @author Игорь Тытарь
 */
public abstract class FsSupport {

    protected final JAXBContext jaxbContext;

    public FsSupport() {
        try {
            jaxbContext = JAXBContext.newInstance(PacketDTO.class, StateDTO.class);
        } catch (JAXBException ex) {
            throw new RuntimeException("can't create JAXBContext", ex);
        }
    }


    protected File getFile(String id) {
        return new File(getDir(), id);
    }

    protected File getDir() {
        File f = Path.of(getPath(), getDirName()).toFile();
        if (!f.exists() && !f.mkdirs()) {
            throw new RuntimeException("can't create directory " + f);
        }
        return f;
    }
    protected abstract String getPath();
    protected abstract String getDirName();
}

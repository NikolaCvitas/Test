package hr.nikola.zip;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class CreateZip {
	
	
	public void createZip() {
		
		
	
	byte[] buffer = new byte[1024];
	
	try{
		
		FileOutputStream fos = new FileOutputStream("C:\\NoviTekst.zip");
		ZipOutputStream zos = new ZipOutputStream(fos);
		ZipEntry ze= new ZipEntry("novi_tekst.txt");
		zos.putNextEntry(ze);
		FileInputStream in = new FileInputStream("C:\\novi_tekst.txt");
	   
		int len;
		while ((len = in.read(buffer)) > 0) {
			zos.write(buffer, 0, len);
		}

		in.close();
		zos.closeEntry();
       
		//remember close it
		zos.close();
      
		System.out.println("Done");

	}catch(IOException ex){
	   ex.printStackTrace();
	}
}
	
    public static void zipFile(File inputFile, String zipFilePath) {
        try {

            // Wrap a FileOutputStream around a ZipOutputStream
            // to store the zip stream to a file. Note that this is
            // not absolutely necessary
            FileOutputStream fileOutputStream = new FileOutputStream(zipFilePath);
            ZipOutputStream zipOutputStream = new ZipOutputStream(fileOutputStream);

            // a ZipEntry represents a file entry in the zip archive
            // We name the ZipEntry after the original file's name
            ZipEntry zipEntry = new ZipEntry(inputFile.getName());
            zipOutputStream.putNextEntry(zipEntry);

            FileInputStream fileInputStream = new FileInputStream(inputFile);
            byte[] buf = new byte[1024];
            int bytesRead;

            // Read the input file by chucks of 1024 bytes
            // and write the read bytes to the zip stream
            while ((bytesRead = fileInputStream.read(buf)) > 0) {
                zipOutputStream.write(buf, 0, bytesRead);
            }

            // close ZipEntry to store the stream to the file
            zipOutputStream.closeEntry();

            zipOutputStream.close();
            fileOutputStream.close();

            System.out.println("Regular file :" + inputFile.getCanonicalPath()+" is zipped to archive :"+zipFilePath);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    
    /**
     * 
     * @param p_dokumenti
     * @param p_nazivDatoteke
     * @return
     * @throws IOException
     */
    private DokumentZaZip zipDokuments( final List<DokumentZaZip> p_dokumenti, final String p_nazivDatoteke ) throws IOException {
        if ( p_dokumenti != null && !p_dokumenti.isEmpty() ) {
        	
            final ByteArrayOutputStream baos = new ByteArrayOutputStream();
            final ZipOutputStream zos = new ZipOutputStream( baos );
            
            for ( final DokumentZaZip dokument : p_dokumenti ) {
                final ZipEntry entry = new ZipEntry( dokument.getNazivDatoteke() );
                final byte[] sadrzajDoc = dokument.getSadrzajDoc();
                entry.setSize( sadrzajDoc.length );
                zos.putNextEntry( entry );
                zos.write( sadrzajDoc );
            }
            zos.closeEntry();
            zos.close();
            final byte[] byteArray = baos.toByteArray();
            final DokumentZaZip result = new DokumentZaZip();
            result.setNazivDatoteke( p_nazivDatoteke );
            result.setSadrzajDoc( byteArray );
            return result;
        }
        return null;
    }
    
    
    

}

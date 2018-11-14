package hr.nikola.zip;

import java.util.Arrays;

public class DokumentZaZip {
	
	private String m_nazivDatoteke= null;
	
	private byte[] m_sadrzajDoc = null;

	/**
	 * 
	 * @return
	 */
	public String getNazivDatoteke() {
		return m_nazivDatoteke;
	}

	/**
	 * 
	 * @param p_nazivDatpteke
	 */
	public void setNazivDatoteke(String p_nazivDatoteke) {
		this.m_nazivDatoteke = p_nazivDatoteke;
	}

	/**
	 * 
	 * @return
	 */
	public byte[] getSadrzajDoc() {
		return m_sadrzajDoc;
	}

	/**
	 * 
	 * @param p_sadrzajDoc
	 */
	public void setSadrzajDoc(byte[] p_sadrzajDoc) {
		this.m_sadrzajDoc = p_sadrzajDoc;
	}

	@Override
	public String toString() {
		return "DokumentZaZip [m_nazivDatpteke=" + m_nazivDatoteke + ", m_sadrzajDoc=" + Arrays.toString(m_sadrzajDoc)
				+ "]";
	}
	
	
	
	

}

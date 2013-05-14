package org.characterbuilder.pdf;

import com.vaadin.server.DownloadStream;
import com.vaadin.server.StreamResource;
import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 *
 * @author <a href="mailto:jens.brimberg@gmail.com">Jens Brimberg</a>
 */
public class ByteArrayResource extends StreamResource {

	private static final long serialVersionUID = 8481793033835252619L;
	private final String filename;
	private final String mimeType;

	public ByteArrayResource(final byte[] byteArray, final String name, final String mimeType)
			  throws FileNotFoundException {
		super(new ByteStreamResource(byteArray), name);

		this.filename = name;
		this.mimeType = mimeType;
		setMIMEType(mimeType);
	}

	@Override
	public DownloadStream getStream() {
		DownloadStream stream = new DownloadStream(getStreamSource().getStream(), mimeType, filename);
		stream.setParameter("Content-Disposition", "attachment;filename=\"" + filename + "\"");
		return stream;
	}

	private static class ByteStreamResource implements StreamResource.StreamSource {

		private static final long serialVersionUID = 8493977974160139309L;
		private final InputStream inputStream;

		public ByteStreamResource(byte[] byteArray) throws FileNotFoundException {
			inputStream = new ByteArrayInputStream(byteArray);
		}

		@Override
		public InputStream getStream() {
			return inputStream;
		}
	}
}
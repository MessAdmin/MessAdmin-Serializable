/**
 *
 */
package clime.messadmin.providers.serializable;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamException;
import java.io.OutputStream;

import clime.messadmin.providers.spi.SerializableProvider;

/**
 * Determines if an object is serializable by serializing it.
 * Note: this is an expensive, although accurate, operation.
 * @author C&eacute;drik LIME
 */
public class FullySerializableProvider implements SerializableProvider {
	private static final OutputStream nullOutputStream = new OutputStream() {
		/** {@inheritDoc} */
		public void write(int b) throws IOException {
		}
		/** {@inheritDoc} */
		public void write(byte[] b, int off, int len) throws IOException {
		}
	};

	/**
	 *
	 */
	public FullySerializableProvider() {
		super();
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean isSerializable(Object obj) {
		try {
			return isFullySerializable(obj);
		} catch (RuntimeException rte) {
			return false;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public int getPriority() {
		return 0;
	}

	/**
	 * Determines if an object is serializable by serializing it.
	 * Note: this is an expensive operation.
	 * @param o
	 * @return true if o is Serializable, false if not
	 */
	public static boolean isFullySerializable(Object o) {
		try {
			ObjectOutputStream out = new ObjectOutputStream(nullOutputStream);
			out.writeObject(o);
			out.flush();
			out.close();
		} catch (ObjectStreamException ose) {
			return false;
		} catch (IOException ioe) {
			//throw new RuntimeException("Exception while computing serialization state: " + ioe);
			return false;
		}
		return true;
	}
}

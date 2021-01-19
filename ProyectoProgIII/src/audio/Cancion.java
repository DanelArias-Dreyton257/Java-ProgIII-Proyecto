package audio;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * 
 * @author danel
 * 
 */
public class Cancion {

	public static final int FINALIZADO = 0;
	public static final int REPRODUCIENDO = 1;
	public static final int PAUSADO = 2;

	private String nombre;
	private Long frameActual = 0L;
	private File ficheroAudio;
	private AudioInputStream audioInputStream;
	private Clip audioClip;
	private int estado = FINALIZADO;
	private boolean bucle = true;

	/**
	 * Crea una cancion a partir del fichero de audio ".wav"
	 * 
	 * @param bucle     poner a True si se quiere la cancion en bucle, sino poner a
	 *                  false
	 * @param audioFile
	 * @throws SongException
	 * @throws NotViableFileException
	 */
	public Cancion(File audioFile, boolean bucle) throws SongException, NotViableFileException {

		if (audioFile.exists() && audioFile.isFile()) {
			this.bucle = bucle;
			this.ficheroAudio = audioFile;
			this.nombre = audioFile.getName();
			try {
				audioInputStream = AudioSystem.getAudioInputStream(ficheroAudio.getAbsoluteFile());
				audioClip = AudioSystem.getClip();
				audioClip.open(audioInputStream);
				if (bucle) {
					audioClip.loop(Clip.LOOP_CONTINUOUSLY);
				}
			} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
				e.printStackTrace();
				throw new SongException("Error abriendo el archivo", e);
			}
		} else
			throw new NotViableFileException("El fichero de audio debe existir y no ser un directorio");
	}

	/**
	 * Crea una cancion a partir del fichero de audio ".wav" La cancion NO sonara en
	 * bucle
	 * 
	 * @param audioFile
	 * @throws SongException
	 * @throws NotViableFileException
	 */
	public Cancion(File audioFile) throws SongException, NotViableFileException {
		this(audioFile, false);
	}

	/**
	 * Inicia la reproduccion de la cancion
	 */
	public void reproducir() {
		audioClip.start();
		estado = REPRODUCIENDO;
	}

	/**
	 * Pausa la reproducion de la cancion solo si estaba reproduciendose, NO la
	 * finaliza
	 */
	public void pausar() {
		if (estado == REPRODUCIENDO) {
			this.frameActual = this.audioClip.getMicrosecondPosition();
			audioClip.stop();
			estado = PAUSADO;
		}
	}

	/**
	 * Reanuda la cancion si estaba pausada
	 * 
	 * @throws SongException
	 */
	public void reanudar() throws SongException {
		if (estado == PAUSADO) {
			audioClip.close();
			resetAudioStream();
			audioClip.setMicrosecondPosition(frameActual);
			this.reproducir();
		}
	}

	/**
	 * Reinicia la cancion
	 * 
	 * @throws SongException
	 */
	public void reiniciar() throws SongException {
		audioClip.stop();
		audioClip.close();
		resetAudioStream();
		frameActual = 0L;
		audioClip.setMicrosecondPosition(0);
		this.reproducir();

	}

	/**
	 * Finaliza la reproduccion de la cancion
	 */
	public void finalizar() {
		frameActual = 0L;
		audioClip.stop();
		audioClip.close();
		estado = FINALIZADO;
	}

	/**
	 * Salta al milisegundo indicado si el punto a saltar de la cancion es positivo
	 * y menor que la duracion de la cancion
	 * 
	 * @param millis
	 * @throws SongException
	 */
	public void saltar(long millis) throws SongException {
		long micros = millis * 1000;
		if (micros > 0 && micros < audioClip.getMicrosecondLength()) {
			audioClip.stop();
			audioClip.close();
			resetAudioStream();
			frameActual = micros;
			audioClip.setMicrosecondPosition(micros);
			this.reproducir();
		} else
			throw new SongException(
					"El punto a saltar de la cancion debe ser positivo y menor que la duracion de la cancion");
	}

	/**
	 * Devuelve la duracion de la cancion en milisegundos
	 * 
	 * @return
	 */
	public long getDuracionEnMillis() {
		return Math.floorDiv(audioClip.getMicrosecondLength(), 1000);
	}

	/**
	 * Devuelve el frame actual de la cancion en milisegundos
	 * 
	 * @return
	 */
	public long getFrameActualEnMillis() {
		return Math.floorDiv(audioClip.getMicrosecondPosition(), 1000);
	}

	/**
	 * Devuelve la duracion de la cancion en microsegundos
	 * 
	 * @return
	 */
	public long getDuracionEnMicros() {
		return audioClip.getMicrosecondLength();
	}

	/**
	 * Devuelve el frame actual de la cancion en microsegundos
	 * 
	 * @return
	 */
	public long getFrameActualEnMicros() {
		return frameActual;
	}

	/**
	 * Resetea el Audio Stream releyendo el fichero
	 * 
	 * @throws SongException
	 */
	private void resetAudioStream() throws SongException {
		try {
			audioInputStream = AudioSystem.getAudioInputStream(ficheroAudio.getAbsoluteFile());
			audioClip.open(audioInputStream);
			if (bucle) {
				audioClip.loop(Clip.LOOP_CONTINUOUSLY);
			}
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			e.printStackTrace();
			throw new SongException("Error reseteando el AudioInputStream", e);
		}
	}
	/**
	 * Devuelve el nombre del archivo
	 * @return
	 */
	public String getNombre() {
		return nombre;
	}
	/**
	 * Devuelve el estado de la cancion
	 * @return
	 */
	public int getEstado() {
		return estado;
	}
	/**
	 * Devuelve si la cancion se reproducira en bucle
	 * @return
	 */
	public boolean isBucle() {
		return bucle;
	}
	/**
	 * Devuelve si la cancion esta siendo reproducida
	 * @return
	 */
	public boolean estaSonando() {
		return audioClip.isRunning();
	}

	/**
	 * 
	 * @author danel
	 *
	 */
	public class NotViableFileException extends Exception {

		private static final long serialVersionUID = 1L;

		public NotViableFileException(String msg) {
			super(msg);
		}

		public NotViableFileException(String msg, Throwable t) {
			super(msg, t);
		}

	}

	/**
	 * 
	 * @author danel
	 *
	 */
	public class SongException extends Exception {

		private static final long serialVersionUID = 1L;

		public SongException(String msg) {
			super(msg);
		}

		public SongException(String msg, Throwable t) {
			super(msg, t);
		}

	}

}

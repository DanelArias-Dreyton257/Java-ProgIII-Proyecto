package audio;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Cancion {

	public static final int FINALIZADO = 0;
	public static final int REPRODUCIENDO = 1;
	public static final int PAUSADO = 2;

	private Long frameActual = 0L;
	private File ficheroAudio;
	private AudioInputStream audioInputStream;
	private Clip audioClip;
	private int estado = FINALIZADO;

	public Cancion(File audioFile) throws SongException, NoExistingFileException {

		if (audioFile.exists()) {
			this.ficheroAudio = audioFile;
			resetAudioStream();

		} else
			throw new NoExistingFileException("El fichero de audio debe existir");
	}

	public void reproducir() {
		audioClip.start();
		estado = REPRODUCIENDO;
	}

	public void pausar() {
		if (estado == REPRODUCIENDO) {
			this.frameActual = this.audioClip.getMicrosecondPosition();
			audioClip.stop();
			estado = PAUSADO;
		}
	}

	public void reanudar() throws SongException {
		if (estado == PAUSADO) {
			audioClip.close();
			resetAudioStream();
			audioClip.setMicrosecondPosition(frameActual);
			this.reproducir();
		}
	}

	public void reiniciar() throws SongException {
		audioClip.stop();
		audioClip.close();
		resetAudioStream();
		frameActual = 0L;
		audioClip.setMicrosecondPosition(0);
		this.reproducir();
		;
	}

	public void finalizar() {
		frameActual = 0L;
		audioClip.stop();
		audioClip.close();
	}

	public void saltar(long millis) throws SongException {
		long micros = millis * 1000;
		if (micros > 0 && micros < audioClip.getMicrosecondLength()) {
			audioClip.stop();
			audioClip.close();
			resetAudioStream();
			frameActual = micros;
			audioClip.setMicrosecondPosition(micros);
			this.reproducir();
		}
	}
	
	public long getDuracionEnMillis() {
		return Math.floorDiv(audioClip.getMicrosecondLength(), 1000);
	}
	
	public long getFrameActualEnMillis() {
		return Math.floorDiv(audioClip.getMicrosecondPosition(), 1000);
	}
	
	public long getDuracionEnMicros() {
		return audioClip.getMicrosecondLength();
	}
	
	public long getFrameActualEnMicros() {
		return frameActual;
	}

	public void resetAudioStream() throws SongException {
		try {
			audioInputStream = AudioSystem.getAudioInputStream(ficheroAudio.getAbsoluteFile());
			audioClip = AudioSystem.getClip();
			audioClip.open(audioInputStream);
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			e.printStackTrace();
			throw new SongException("Error reseteando el AudioInputStream", e);
		}
	}

	public class NoExistingFileException extends Exception {

		private static final long serialVersionUID = 1L;

		public NoExistingFileException(String msg) {
			super(msg);
		}

		public NoExistingFileException(String msg, Throwable t) {
			super(msg, t);
		}

	}

	public class SongException extends Exception {

		private static final long serialVersionUID = 1L;

		public SongException(String msg) {
			super(msg);
		}

		public SongException(String msg, Throwable t) {
			super(msg, t);
		}

	}

	public static void main(String[] args) {

		Cancion c;
		try {
			c = new Cancion(new File("C:/Users/danel/Downloads/Violet Sky.wav"));
			c.reproducir();
			Thread.sleep(100000);
		} catch (SongException | NoExistingFileException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}

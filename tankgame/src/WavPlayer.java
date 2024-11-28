import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

    public class WavPlayer implements Runnable {
        private String filePath;
        private volatile boolean running = true;
        private Thread playerThread;

        public WavPlayer(String filePath) {
            this.filePath = filePath;
        }

        public void startPlaying() {
            if (playerThread == null || !playerThread.isAlive()) {
                playerThread = new Thread(this);
                playerThread.start();
            }
        }

        public void stopPlaying() {
            running = false;
            if (playerThread != null) {
                try {
                    playerThread.join(); // 等待线程结束
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }

        @Override
        public void run() {
            while (running) {
                playAudio();
            }
        }

        private void playAudio() {
            try {
                File audioFile = new File(filePath);
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
                AudioFormat format = audioStream.getFormat();
                DataLine.Info info = new DataLine.Info(Clip.class, format);

                Clip audioClip = (Clip) AudioSystem.getLine(info);
                audioClip.open(audioStream);
                audioClip.start();

                // 等待音频播放完成
                audioClip.addLineListener(event -> {
                    if (event.getType() == LineEvent.Type.STOP) {
                        audioClip.close();
                    }
                });

                // 等待音频播放结束
                while (audioClip.isRunning()) {
                    Thread.sleep(100);
                }

                audioClip.close();
                audioStream.close();

            } catch (UnsupportedAudioFileException e) {
                System.err.println("Unsupported audio file format.");
                e.printStackTrace();
            } catch (IOException e) {
                System.err.println("Error playing audio file.");
                e.printStackTrace();
            } catch (LineUnavailableException e) {
                System.err.println("Audio line unavailable.");
                e.printStackTrace();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
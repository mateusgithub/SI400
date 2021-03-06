/**
 * HausPlant
 *
 * 2017
 * Equipe desenvolvedora do HausPlant
 */
package tk.hausplant.view;

import spacedrawboard.resource.Vector3D;
import spacedrawboard.visualization.Drawboard;
import spacedrawboard.visualization.Visualization;
import tk.hausplant.model.Movel;
import tk.hausplant.model.Parede;
import tk.hausplant.model.Planta;

public class Renderizador3DPlanta {

	/**
	 * Distância padrão inicial da câmera em metros
	 */
	private static final int DISTANCIA_CAMERA_PADRAO = 110;

	/**
	 * Planta a ser renderizada
	 */
	private final Planta planta;

	/**
	 * Objeto da biblioteca SpaceDrawboard, onde serão postos os objetos 3D
	 */
	private final Drawboard drawboard = new Drawboard();

	/**
	 * Flag para marcar se a planta já foi construída na drawboard
	 */
	private boolean construido = false;

	public Renderizador3DPlanta(final Planta planta) {
		this.planta = planta;
	}

	private void construirCasa() {
		if (construido) {
			return;
		}
		construido = true;

		// Construir paredes
		for (Parede parede : planta.getParedes()) {
			parede.desenhar3DEm(drawboard);
		}

		// Inserir móveis
		for (Movel movel : planta.getMoveis()) {
			movel.desenhar3DEm(drawboard);
		}
	}

	public void mostrarVisualizacao() {
		construirCasa();

		// Pixel por metro
		double pxMetro = Prancheta.PIXELS_POR_METRO;

		// Centralizar visualização
		double somaX = 0;
		double somaY = 0;

		for (Parede parede : planta.getParedes()) {
			somaX += (double) (parede.getA().x + parede.getB().x) / 2;
			somaY += (double) (-parede.getA().y - parede.getB().y) / 2;
		}

		for (Movel movel : planta.getMoveis()) {
			somaX += movel.getX();
			somaY += movel.getY();
		}

		double centroX = 0;
		double centroY = 0;

		int numeroComponentes = planta.getParedes().size() + planta.getMoveis().size();

		if (numeroComponentes > 0) {
			centroX = (somaX / numeroComponentes) / pxMetro;
			centroY = (somaY / numeroComponentes) / pxMetro;
		}

		// Mostrar visualização da cena
		Visualization visualizacao = new Visualization("Planta", 900, 600, drawboard);
		visualizacao.setFocusPoint(new Vector3D(centroX, centroY, Parede.ALTURA_PADRAO / 2));
		visualizacao.setCameraDistance(DISTANCIA_CAMERA_PADRAO);
		visualizacao.setCameraLatitudeAngle(35);
		visualizacao.setCameraLongitudeAngle(35);
		visualizacao.setZoomSpeed(3);
		visualizacao.setDragSpeed(0.025);
		visualizacao.showWindow();
	}

}

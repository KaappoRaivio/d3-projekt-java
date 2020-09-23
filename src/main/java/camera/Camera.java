package camera;

import misc.Vector2D;

public class Camera {
    private Vector2D cameraPosition;
    private Vector2D viewportDimensions;

    public Vector2D getCameraPosition() {
        return cameraPosition;
    }

    public Vector2D getViewportDimensions() {
        return viewportDimensions;
    }

    public Camera(Vector2D cameraPosition, Vector2D viewportDimensions) {
        this.cameraPosition = cameraPosition;
        this.viewportDimensions = viewportDimensions;
    }

    public boolean isVisibleInViewport(Vector2D position, Vector2D size) {
        boolean topLeft = cameraPosition.getI() <= position.getI() + size.getI()
                && cameraPosition.getJ() <= position.getJ() + size.getJ();

        boolean bottomRight = (cameraPosition.getI() + viewportDimensions.getI()) > position.getI()
                && (cameraPosition.getJ() + viewportDimensions.getJ()) > position.getJ();

        return topLeft && bottomRight;
    }

    public void setCameraPosition (Vector2D cameraPosition) {
        this.cameraPosition = cameraPosition.add(viewportDimensions.multiply(-0.5));
    }

    public void setViewportDimensions (Vector2D viewportDimensions) {
        this.viewportDimensions = viewportDimensions;
    }
}

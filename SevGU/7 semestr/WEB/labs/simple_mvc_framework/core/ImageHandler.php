<?php

namespace core;


use Config;
use core\exceptions\ImageException;

/**
 * Class ImageHandler
 * @package core
 */
class ImageHandler {
    private $_image;
    private $_image_type;

    /**
     * Load image
     *
     * @param string $filename
     * @throws ImageException
     */
    public function __construct(string $filename)
    {
        $image_info = getimagesize($filename);
        $this->_image_type = $image_info[2];
        // Create image
        switch ($this->_image_type) {
            case IMAGETYPE_JPEG:
                $this->_image = imagecreatefromjpeg($filename);
                break;
            case IMAGETYPE_GIF:
                $this->_image = imagecreatefromgif($filename);
                break;
            case IMAGETYPE_PNG:
                $this->_image = imagecreatefrompng($filename);
                break;
            default:
                throw new ImageException("`This type $this->_image_type does not supported`", E_USER_ERROR);
        }
    }

    /**
     * Get image extension
     */
    public function getExtension()
    {
        switch ($this->_image_type) {
            case IMAGETYPE_JPEG:
                return 'jpg';
            case IMAGETYPE_GIF:
                return 'gif';
            case IMAGETYPE_PNG:
                return 'png';
            default:
                return 'unknown';
        }
    }

    /**
     * Save image to server
     *
     * @param string $filename
     * @param int|null $image_type
     * @param int $compression
     * @param int|null $permissions
     * @throws ImageException
     */
    public function save(string $filename, int $image_type = null, int $compression = 75,
                         int $permissions = null)
    : void {
        $path = Config::IMAGE_DIR . $filename;
        $image_type = $image_type ?? $this->_image_type;
        $this->resizeToRequired();
        switch ($image_type) {
            case IMAGETYPE_JPEG:
                imagejpeg($this->_image, $path, $compression);
                break;
            case IMAGETYPE_GIF:
                imagegif($this->_image, $path);
                break;
            case IMAGETYPE_PNG:
                imagepng($this->_image, $path);
                break;
            default:
                throw new ImageException("`This type $this->_image_type does not supported`", E_USER_ERROR);
        }
        // Set permissions
        if (!is_null($permissions)) {
            chmod($filename, $permissions);
        }
    }

    /**
     * Show image without saving
     *
     * @param int $image_type
     * @throws ImageException
     */
    public function output(int $image_type = IMAGETYPE_JPEG)
    : void {
        switch ($image_type) {
            case IMAGETYPE_JPEG:
                imagejpeg($this->_image);
                break;
            case IMAGETYPE_GIF:
                imagegif($this->_image);
                break;
            case IMAGETYPE_PNG:
                imagepng($this->_image);
                break;
            default:
                throw new ImageException("`This type $this->_image_type does not supported`", E_USER_ERROR);
        }
    }

    /**
     * Get width
     *
     * @return int
     */
    public function getWidth()
    : int {
        return imagesx($this->_image);
    }

    /**
     * Get height
     *
     * @return int
     */
    public function getHeight()
    : int {
        return imagesy($this->_image);
    }

    /**
     * Resize height
     *
     * @param int $height
     */
    public function resizeToHeight(int $height)
    : void {
        $ratio = $height / $this->getHeight();
        $width = $this->getWidth() * $ratio;
        $this->resize($width, $height);
    }

    /**
     * Resize width
     *
     * @param int $width
     */
    public function resizeToWidth(int $width)
    : void {
        $ratio = $width / $this->getWidth();
        $height = $this->getheight() * $ratio;
        $this->resize($width, $height);
    }

    /**
     * Scale by percent
     *
     * @param int $scale
     */
    public function scale(int $scale)
    : void {
        $width = $this->getWidth() * $scale/100;
        $height = $this->getheight() * $scale/100;
        $this->resize($width, $height);
    }

    /**
     * Resize width and height
     *
     * @param int $width
     * @param int $height
     */
    public function resize(int $width, int $height)
    : void {
        $new_image = imagecreatetruecolor($width, $height);
        imagecopyresampled($new_image, $this->_image, 0, 0, 0, 0, $width, $height,
            $this->getWidth(), $this->getHeight());
        $this->_image = $new_image;
    }

    /**
     * Resize to required size
     */
    private function resizeToRequired()
    : void {
        // Resize width
        if ($this->getWidth() > Config::IMAGE_MAX_WIDTH) {
            $this->resizeToWidth(Config::IMAGE_MAX_WIDTH);
        }
        // Resize height
        if ($this->getHeight() > Config::IMAGE_MAX_HEIGHT) {
            $this->resizeToHeight(Config::IMAGE_MAX_HEIGHT);
        }
    }
}

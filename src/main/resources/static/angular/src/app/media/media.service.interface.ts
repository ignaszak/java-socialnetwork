import {Media} from "./media";
import {NgxGalleryImage, NgxGalleryOptions} from "ngx-gallery";

export interface MediaServiceInterface {

    getUrl(media: Media): string;
    getThumbnailUrl(media: Media): string;
    getNgxGalleryOptions(medias: Media[]): NgxGalleryOptions[];
    getNgxGalleryImages(medias: Media[]): NgxGalleryImage[];
    deleteMedia(media: Media);
}

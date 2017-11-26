import {Post} from "../post/post";
import {Media} from "./media";
import {NgxGalleryImage, NgxGalleryOptions} from "ngx-gallery";

export interface MediaServiceInterface {

    getMediasByPost(post: Post): Promise<Media[]>;
    getUrl(media: Media): string;
    getThumbnailUrl(media: Media): string;
    getNgxGalleryOptions(medias: Media[]): NgxGalleryOptions[];
    getNgxGalleryImages(medias: Media[]): NgxGalleryImage[];
}

import {MediaServiceInterface} from "./media.service.interface";
import {Post} from "../post/post";
import {Media} from "./media";
import {Inject, Injectable} from "@angular/core";
import {RestProviderInterface} from "../rest/rest-provider.interface";
import {Http} from "@angular/http";
import {RestProvider} from "../rest/rest-provider";
import 'hammerjs';
import {NgxGalleryAnimation, NgxGalleryImage, NgxGalleryOptions} from "ngx-gallery";

@Injectable()
export class MediaService implements MediaServiceInterface {

    constructor(
        private http: Http,
        @Inject('RestProviderInterface') private provider: RestProviderInterface
    ) {}

    getMediasByPost(post: Post): Promise<Media[]> {
        let path = this.provider.getPath(RestProvider.POSTS_MEDIAS);
        return this.http.get(path)
            .toPromise()
            .then(response => response.json() as Media)
            .catch(RestProvider.handleError);
    }

    getUrl(media: Media): string {
        return RestProvider.PUBLIC_MEDIAS + '/' + media.filename;
    }

    getThumbnailUrl(media: Media): string {
        return RestProvider.PUBLIC_MEDIAS + '/thumbnail-' + media.filename;
    }

    getNgxGalleryOptions(medias: Media[]): NgxGalleryOptions[] {
        let isSingleMedia: boolean = medias.length === 1;
        return [
            {
                thumbnails: ! isSingleMedia,
                imageArrows: ! isSingleMedia,
                width: '100%',
                height: '400px',
                thumbnailsColumns: 4,
                imageAnimation: NgxGalleryAnimation.Slide,
                imageSwipe: true,
                previewSwipe: true,
                previewCloseOnEsc: true,
                previewKeyboardNavigation: true,
                thumbnailsRemainingCount: true
            },
            // max-width 800
            {
                breakpoint: 800,
                width: '100%',
                height: '600px',
                imagePercent: 80,
                thumbnailsPercent: 20,
                thumbnailsMargin: 20,
                thumbnailMargin: 20
            },
            // max-width 400
            {
                breakpoint: 400,
                preview: false
            }
        ];
    }

    getNgxGalleryImages(medias: Media[]): NgxGalleryImage[] {
        let result: NgxGalleryImage[] = [];
        medias.forEach(media => {
            let original: string = this.getUrl(media);
            result.push({
                small:  this.getThumbnailUrl(media),
                medium: original,
                big:    original
            });
        });
        return result;
    }
}

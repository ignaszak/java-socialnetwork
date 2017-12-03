import {MediaServiceInterface} from "./media.service.interface";
import {Media} from "./media";
import {Inject, Injectable} from "@angular/core";
import {RestProvider} from "../rest/rest-provider";
import 'hammerjs';
import {NgxGalleryAnimation, NgxGalleryImage, NgxGalleryOptions} from "ngx-gallery";
import {Http} from "@angular/http";
import {RestProviderInterface} from "../rest/rest-provider.interface";

@Injectable()
export class MediaService implements MediaServiceInterface {

    constructor(
        private http: Http,
        @Inject('RestProviderInterface') private provider: RestProviderInterface
    ) {}

    deleteMedia(media: Media): void {
        let path: string = this.provider.getPath(RestProvider.POSTS_MEDIA, {'id': media.id});
        this.http.delete(path).subscribe();
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

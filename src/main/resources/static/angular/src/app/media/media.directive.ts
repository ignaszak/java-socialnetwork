import {Directive, ElementRef, OnChanges, Renderer} from "@angular/core";
import {User} from "../user/user";
import {Media} from "./media";
import {RestProvider} from "../rest/rest-provider";

@Directive({
    selector: `[media]`,
    inputs:   ['media']
})
export class MediaDirective implements OnChanges {
    media: Media = new Media();

    constructor(private element: ElementRef, private renderer: Renderer) {
    }

    ngOnChanges() {
        if (this.media && this.media.filename) {
            this.renderer.setElementProperty(
                this.element.nativeElement,
                'src',
                RestProvider.PUBLIC_MEDIAS + '/' + this.media.filename
            );
        }
    }
}

@Directive({
    selector: `[mediaThumbnail]`,
    inputs:   ['mediaThumbnail']
})
export class MediaThumbnailDirective implements OnChanges {
    mediaThumbnail: Media = new Media();

    constructor(private element: ElementRef, private renderer: Renderer) {
    }

    ngOnChanges() {
        if (this.mediaThumbnail && this.mediaThumbnail.filename) {
            this.renderer.setElementProperty(
                this.element.nativeElement,
                'src',
                RestProvider.PUBLIC_MEDIAS + '/thumbnail-' + this.mediaThumbnail.filename
            );
        }
    }
}

@Directive({
    selector: `[mediaProfileThumbnail]`,
    inputs:   ['mediaProfileThumbnail']
})
export class MediaProfileThumbnailDirective implements OnChanges {
    mediaProfileThumbnail: User = new User;

    constructor(private element: ElementRef, private renderer: Renderer) {
    }

    ngOnChanges() {
        if (this.mediaProfileThumbnail && this.mediaProfileThumbnail.profile) {
            this.renderer.setElementProperty(
                this.element.nativeElement,
                'src',
                RestProvider.PUBLIC_MEDIAS + '/profile-thumbnail-' + this.mediaProfileThumbnail.profile
            );
        } else {
            this.renderer.setElementProperty(
                this.element.nativeElement,
                'src',
                '/public/angular/assets/img/profile.png'
            );
        }
    }
}

export const MEDIA_DIRECTIVES = [MediaDirective, MediaThumbnailDirective, MediaProfileThumbnailDirective];

import {Directive, ElementRef, OnChanges, Renderer} from "@angular/core";
import {User} from "../../user/user";

@Directive({
    selector: `[media]`,
    inputs:   ['media']
})
export class MediaDirective implements OnChanges {
    media: string;

    constructor(private element: ElementRef, private renderer: Renderer) {
    }

    ngOnChanges() {
        if (this.media) {
            this.renderer.setElementProperty(
                this.element.nativeElement,
                'src',
                '/public/medias/' + this.media
            );
        }
    }
}

@Directive({
    selector: `[mediaThumbnail]`,
    inputs:   ['mediaThumbnail']
})
export class MediaThumbnailDirective implements OnChanges {
    mediaThumbnail: string;

    constructor(private element: ElementRef, private renderer: Renderer) {
    }

    ngOnChanges() {
        if (this.mediaThumbnail) {
            this.renderer.setElementProperty(
                this.element.nativeElement,
                'src',
                '/public/medias/thumbnail-' + this.mediaThumbnail
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
                '/public/medias/profile-thumbnail-' + this.mediaProfileThumbnail.profile
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

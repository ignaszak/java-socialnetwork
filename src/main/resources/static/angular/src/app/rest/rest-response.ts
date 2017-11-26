import {Response} from "@angular/http";

export class RestResponse <T> {

    private json: any;

    constructor (response: Response) {
        this.json = response.json();
    }

    public getResponse(): Array<T> {
        return this.json.content || null;
    }

    public getPageSize(): number {
        return this.json.size;
    }

    public getTotalPages(): number {
        return this.json.totalPages;
    }

    public getTotalElements(): number {
        return this.json.totalElements;
    }

    public hasNextPage(): boolean {
        return ! this.json.last;
    }

    public getNextPage(): number {
        return this.hasNextPage() ? ++ this.json.number : null;
    }

    public getCurrentPage(): number {
        return this.json.number;
    }

}

import swal from 'sweetalert2';

export class Swal {

    public static confirm(onConfirmed: any, title: string = 'Are you sure?'): void {
        swal({
            title: title,
            type: 'warning',
            showCancelButton: true
        }).then(onConfirmed).catch(swal.noop);
    }

    public static error(title: string = 'Oops...', description: string = 'Something went wrong!'): void {
        swal({
            title: title,
            html: description,
            type: 'error'
        })
    }

    public static success(title: string = 'Success!', description: string = ''): void {
        swal({
            title: title,
            html: description,
            type: 'success'
        })
    }
}

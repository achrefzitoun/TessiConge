import { Component, OnInit } from '@angular/core';
import { Product } from 'src/app/demo/api/product';
import { MessageService } from 'primeng/api';
import { Table } from 'primeng/table';
import { ProductService } from 'src/app/demo/service/product.service';
import { MotifRefus } from 'src/app/Model/MotifRefus';
import { MotifsService } from 'src/app/Services/Motifs/motifs.service';
import { TypeMotif } from 'src/app/Model/TypeMotif';

@Component({
  templateUrl: './motif-refus.component.html',
  providers: [MessageService]
})
export class MotifRefusComponent implements OnInit {

  motifDialog: boolean = false;

  deleteMotifDialog: boolean = false;

  deleteMotifsDialog: boolean = false;

  motifs: MotifRefus[] = [];

  motif: MotifRefus;

  selectedMotifs: Product[] = [];

  submitted: boolean = false;

  cols: any[] = [];

  statuses: any[] = [];


  rowsPerPageOptions = [5, 10, 20];

  constructor(private messageService: MessageService, private motifserv: MotifsService) { }

  ngOnInit() {
    this.motifserv.getMotif().subscribe({
      next: (data) =>
        this.motifs = data,

    });


  }

  openNew() {
    this.motif = new MotifRefus()
    this.submitted = false;
    this.motifDialog = true;
  }

  deleteSelectedMotifs() {
    this.deleteMotifsDialog = true;
  }

  editMotif(motif: MotifRefus) {
    this.motif = { ...motif };
    this.motifDialog = true;
  }

  deleteMotif(id: number) {
    this.deleteMotifDialog = true;
    this.motifserv.getMotifById(id).subscribe(
      (data: MotifRefus) => {
        this.motif = data;
      })
    
  }

  confirmDeleteSelected() {
    this.deleteMotifsDialog = false;
    this.motifs = this.motifs.filter(val => !this.selectedMotifs.includes(val));
    this.messageService.add({ severity: 'success', summary: 'Successful', detail: 'Products Deleted', life: 3000 });
    this.selectedMotifs = [];
  }

  confirmDelete(id:number) {
    this.deleteMotifDialog = false;
    this.motifserv.deleteMotif(id).subscribe();
    window.location.reload();
    this.messageService.add({ severity: 'success', summary: 'Successful', detail: 'Congé Annulé', life: 5000 });
  }

  hideDialog() {
    this.motifDialog = false;
    this.submitted = false;
    this.motif=null
  }

  saveMotif() {
    this.submitted = true;


    if (this.motif.idMotif!= null) {
      
      this.motifserv.updateMotif(this.motif).subscribe(() => {
        this.messageService.add({ severity: 'success', summary: 'Successful', detail: 'Motif Created', life: 3000 });
      });
    } 
    else {
      this.motifserv.addMotif(this.motif).subscribe(() => {
        this.messageService.add({ severity: 'success', summary: 'Successful', detail: 'Motif Created', life: 3000 });
      });
    }
    

    this.motifs = [...this.motifs];
    this.motifDialog = false;
    this.motif = new MotifRefus;

  }
  
  onGlobalFilter(table: Table, event: Event) {
    table.filterGlobal((event.target as HTMLInputElement).value, 'contains');
  }

}

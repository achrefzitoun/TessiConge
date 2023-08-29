import { Component } from '@angular/core';
import { CongeServicesService } from 'src/app/Services/Conge/conge-services.service';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-export-excel',
  templateUrl: './export-excel.component.html'
})
export class ExportExcelComponent {
  date1: Date;
  date2: Date;

  constructor(private congeServices: CongeServicesService, private datePipe: DatePipe) { }

  ngOnInit() {
    this.date1 = null;
    this.date2 = null;
  }

  save(){
    var dateDebut = new Date(this.date1);
    var dateFin = new Date(this.date2);
    const formattedDate1 = this.datePipe.transform(dateDebut, "yyyy-MM-dd'T'HH:mm:ss");
    const formattedDate2 = this.datePipe.transform(dateFin, "yyyy-MM-dd'T'HH:mm:ss");

  }

  exportConge() {
    var formattedDate1 = "";
    var formattedDate2 = "";
    if(this.date1 !=null && this.date2!=null)
    {
    var dateDebut = new Date(this.date1);
    var dateFin = new Date(this.date2);
    formattedDate1 = this.datePipe.transform(dateDebut, "yyyy-MM-dd'T'HH:mm:ss");
    formattedDate2 = this.datePipe.transform(dateFin, "yyyy-MM-dd'T'HH:mm:ss");
  }
    this.congeServices.exportExcel(formattedDate1,formattedDate2).subscribe(response => {
      const blob = new Blob([response.body], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' });
      const url = window.URL.createObjectURL(blob);
      const a = document.createElement('a');
      a.href = url;
      a.download = 'Liste_Des_Conges.xlsx';
      a.click();
      window.URL.revokeObjectURL(url);
    });
  }
}

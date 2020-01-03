package br.ufpi.dadosabertosapi.schedules;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import br.ufpi.dadosabertosapi.services.OpenDataService;

@Component
@EnableScheduling
public class UploadFileSchedules {
	
	@Autowired
	private OpenDataService dadosService;
	
	@Scheduled(cron="0 50 0 * * ?")
	private void updloadFiles() {
		
		dadosService.uploadAllFiles();
		
	}

}

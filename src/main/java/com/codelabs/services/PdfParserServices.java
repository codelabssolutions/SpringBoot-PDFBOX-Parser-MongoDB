package com.codelabs.services;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.pdfbox.contentstream.operator.color.SetNonStrokingColor;
import org.apache.pdfbox.contentstream.operator.color.SetNonStrokingColorN;
import org.apache.pdfbox.contentstream.operator.color.SetNonStrokingColorSpace;
import org.apache.pdfbox.contentstream.operator.color.SetNonStrokingDeviceCMYKColor;
import org.apache.pdfbox.contentstream.operator.color.SetNonStrokingDeviceGrayColor;
import org.apache.pdfbox.contentstream.operator.color.SetNonStrokingDeviceRGBColor;
import org.apache.pdfbox.contentstream.operator.color.SetStrokingColor;
import org.apache.pdfbox.contentstream.operator.color.SetStrokingColorN;
import org.apache.pdfbox.contentstream.operator.color.SetStrokingColorSpace;
import org.apache.pdfbox.contentstream.operator.color.SetStrokingDeviceCMYKColor;
import org.apache.pdfbox.contentstream.operator.color.SetStrokingDeviceGrayColor;
import org.apache.pdfbox.contentstream.operator.color.SetStrokingDeviceRGBColor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.pdmodel.graphics.color.PDColor;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;
import org.apache.pdfbox.text.TextPosition;
import org.apache.pdfbox.tools.imageio.ImageIOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codelabs.entity.ArritmiasSupraventriculares;
import com.codelabs.entity.ArritmiasVentriculares;
import com.codelabs.entity.DadosDoExame;
import com.codelabs.entity.DadosDoPaciente;
import com.codelabs.entity.DepressaoDoST;
import com.codelabs.entity.ElevacaoDoST;
import com.codelabs.entity.ExemplosDeECG;
import com.codelabs.entity.FirstPdfData;
import com.codelabs.entity.FrequenciaCardiaca;
import com.codelabs.entity.LaudoMedico;
import com.codelabs.entity.MedicoSolicitante;
import com.codelabs.entity.Pausas;
import com.codelabs.entity.RelatorioTabular;
import com.codelabs.entity.ResumoEstatstico;
import com.codelabs.entity.Totais;
import com.codelabs.main.ApplicationConstants;
import com.codelabs.repository.DaDosdoExameRepository;
import com.codelabs.repository.ExemplosDeECGRepository;
import com.codelabs.repository.LaudoMedicoRepository;
import com.codelabs.repository.RelatorioTabularRepository;
import com.idpdc.util.MongoUtils;

@Service
public class PdfParserServices extends PDFTextStripper{
	private static final String EMPTY_STRING = "";
	private String page_ignore = null;
	int linenumber = 0;
	PDColor currentStyle = null;
	boolean dadosdoExameStart = false;
	boolean dadosdoExameEnd = false;
	boolean edustart = false;
	boolean eduend = false;
	boolean dadosDoPacienteStart = false;
	boolean dadosDoPacienteEnd = false;
	public String dadosdoExame = "";
	boolean medicaSolicitanteStart = false;
	boolean medicaSolicitanteEnd = false;
	boolean resumoEstatsticoStart = false;
	boolean resumoEstatsticoEnd = false;
	boolean laudMedicoStart = false;
	boolean exemplosDeECGEnd = false;
	boolean exemplosDeECGStart = false;
	boolean ludoMedicoEnd = false;
	boolean artefatos = false;
	int artefatosCount = 1;
	public String pageNo1 = "";
	public String titcomptextcurrent = "";
	public String titcomptextprevious = "";
	boolean titlecompanyend = false;
	boolean institutionstatus = false;
	boolean degreeandspecializationstatus = false;
	boolean descriptionend = false;
	int expcounter = 0;
	int arritmiasVen = 1;
	int arritmiasSup = 1;
	int arritmiasSupCount = 1;
    public String usereducation = "";
	public String userexperience = "";
	
	String previousText;
	String summary = "";
	String ddpString = "";
	int jobtitlenumber = 0;
	int educounter = 0;
	int institutionlinenumber = 0;
	String expdescription = "";
	String exptitle = "";
	String expcompany = "";
	boolean expiscurrent = false;
	boolean exprecordstart = false;
	boolean exprecordend = false;
	String expstartdate = "";
	String expenddate = "";
	// EDU
	String edudescription = "";
	String eduyearstart = "";
	String eduyearend = "";
	String edudegree = "";
	String eduspecialization = "";
	String eduinstitution = "";
	final double res = 72; // PDF units are at 72 DPI
	StringBuilder textBuilder = new StringBuilder();
	Map<TextPosition, PDColor> textColorMap = new HashMap<TextPosition, PDColor>();
	FirstPdfData firstPdfData =new FirstPdfData();  
	DadosDoExame dde = new DadosDoExame() ;
	DadosDoPaciente ddp= new DadosDoPaciente();
	MedicoSolicitante ms= new MedicoSolicitante();
	ResumoEstatstico re = new ResumoEstatstico();
	Totais totais =new Totais();
	FrequenciaCardiaca frequenciaCardiaca =new FrequenciaCardiaca();
	ArritmiasVentriculares arritmiasVentriculares=new ArritmiasVentriculares();
	ArritmiasSupraventriculares arritmiasSupraventriculares =new ArritmiasSupraventriculares();
	DepressaoDoST depressaoDoST=new DepressaoDoST();
	Pausas pausas =new Pausas();
	ElevacaoDoST elevacaoDoST=new ElevacaoDoST();
	LaudoMedico laudoMedico =new LaudoMedico();
	RelatorioTabular relatorioTabular=new RelatorioTabular();
	 
	@Autowired
	DaDosdoExameRepository repository;
	@Autowired
	LaudoMedicoRepository laudoMedicoRepository;
	@Autowired
	RelatorioTabularRepository relatorioTabularRepository;
	@Autowired
	ExemplosDeECGRepository exemplosDeECGRepository;
   
    public void pdfPars() throws Exception {
    	  parseDocument("F:\\JUSTMENTOR_WORKSPACE\\pdfparser1\\docs\\med\\16212 - MARCELO STEINEMEYER PRADA 533973.pdf");
		   firstPdfData.setId(MongoUtils.getUId());
		  dde.setId(MongoUtils.getUId());
		  firstPdfData.setDadosDoExame(dde); 
		  firstPdfData.setDadosDoPaciente(ddp);
		  firstPdfData.setMedicoSolicitante(ms);
		  re.setTotais(totais);
		  re.setDepressaoDoST(depressaoDoST);
		  re.setArritmiasVentriculares(arritmiasVentriculares);
		  re.setFrequenciaCardiaca(frequenciaCardiaca);
		  re.setPausas(pausas);
		  re.setArritmiasSupraventriculares(arritmiasSupraventriculares);
		  re.setElevacaoDoST(elevacaoDoST);
		  firstPdfData.setResumoEstatstico(re);
		  repository.insert(firstPdfData);
		  laudoMedico.setId(MongoUtils.getUId());
		  laudoMedicoRepository.insert(laudoMedico);
		  
    }
    
   
	public PdfParserServices() throws IOException {
		   
	        addOperator(new SetStrokingColorSpace());
			addOperator(new SetNonStrokingColorSpace());
			addOperator(new SetStrokingDeviceCMYKColor());
			addOperator(new SetNonStrokingDeviceCMYKColor());
			addOperator(new SetNonStrokingDeviceRGBColor());
			addOperator(new SetStrokingDeviceRGBColor());
			addOperator(new SetNonStrokingDeviceGrayColor());
			addOperator(new SetStrokingDeviceGrayColor());
			addOperator(new SetStrokingColor());
			addOperator(new SetStrokingColorN());
			addOperator(new SetNonStrokingColor());
			addOperator(new SetNonStrokingColorN());
			page_ignore = "page ";
	 } 
	
	 public void parseDocument(String filePath) throws InvalidPasswordException, IOException {
			try (PDDocument document = PDDocument.load(new File(filePath))) {
					setSortByPosition(true);
					setStartPage(0);
					setEndPage(document.getNumberOfPages());
					Writer dummy = new OutputStreamWriter(new ByteArrayOutputStream());
					parsedRelatorioTabular(document, document.getNumberOfPages());
					parsedRelatorioECG(document, document.getNumberOfPages());
					getImagesFromPDF(document,5);
					getImagesFromPDF(document,4);
					getImagesFromPDF(document,6);
					getImagesFromPDF(document,7);
					getImagesFromPDF(document,8);
					getImagesFromPDF(document,9);
					getImagesFromPDF(document,10);
					getImagesFromPDF(document,11);
					getImagesFromPDF(document,12);
					getImagesFromPDF(document,13);
					getImagesFromPDF(document,14);
					
		     		//readParaFromPDF(document, 3, 4, 22);
					writeText(document, dummy);
			}
		}

		private PDColor determineStyleColor(TextPosition textPosition) {

			return textColorMap.get(textPosition);

		}

		private void parsedRelatorioECG(PDDocument document, int numberOfPages) throws IOException {

			PDFTextStripperByArea stripper = new PDFTextStripperByArea();
			stripper.setSortByPosition(true);
			int hx = 140;
			for (int i = 0; i <= 14; i++) {
				ExemplosDeECG exemplosDeECG=new ExemplosDeECG();
				Rectangle rect1 = new Rectangle(30, hx + (i * 15), 45, 10);
				Rectangle rect2 = new Rectangle(70, hx + (i * 15), 40, 10);
				Rectangle rect3 = new Rectangle(145, hx + (i * 15), 40, 10);
				Rectangle rect4 = new Rectangle(250, hx + (i * 15), 40, 10);
				Rectangle rect5 = new Rectangle(320, hx + (i * 15), 150, 10);
				Rectangle rect6 = new Rectangle(470, hx + (i * 15), 40, 10);
				Rectangle rect7 = new Rectangle(510, hx + (i * 15), 40, 10);
				stripper.addRegion("row1column1", rect1);
				stripper.addRegion("row1column2", rect2);
				stripper.addRegion("row1column3", rect3);
				stripper.addRegion("row1column4", rect4);
				stripper.addRegion("row1column5", rect5);
				stripper.addRegion("row1column6", rect6);
				stripper.addRegion("row1column7", rect7);
				PDPage firstPage = document.getPages().get(4);
				stripper.extractRegions(firstPage);
				exemplosDeECG.setHorario(stripper.getTextForRegion("row1column1"));
				exemplosDeECG.setDur(stripper.getTextForRegion("row1column2"));
				exemplosDeECG.setAtividate(stripper.getTextForRegion("row1column3"));
				exemplosDeECG.setSintomas(stripper.getTextForRegion("row1column4"));
				exemplosDeECG.setDiagnostico(stripper.getTextForRegion("row1column5"));
				exemplosDeECG.setBasal(stripper.getTextForRegion("row1column6"));
				exemplosDeECG.setFc(stripper.getTextForRegion("row1column7"));
				exemplosDeECGRepository.insert(exemplosDeECG);
				
			/*
			 * System.out.println("=1=" + stripper.getTextForRegion("row1column1") + "=2=" +
			 * stripper.getTextForRegion("row1column2") + "=3=" +
			 * stripper.getTextForRegion("row1column3") + "=4=" +
			 * stripper.getTextForRegion("row1column4") + "=5=" +
			 * stripper.getTextForRegion("row1column5") + "=6=" +
			 * stripper.getTextForRegion("row1column6") + "==7=" +
			 * stripper.getTextForRegion("row1column7"));
			 */
			}
		}

		public void getImagesFromPDF(PDDocument document,int pageName) throws IOException {
			 try {
		        	
				 if(pageName==5) {
				    PDFRenderer pdfRenderer = new PDFRenderer(document);
		        	BufferedImage bim = pdfRenderer.renderImageWithDPI(pageName, 300, ImageType.RGB).getSubimage(0,400, 2400 , 1400 );
		       	    //Suffix in filename will be used as the file format
		        	ImageIOUtil.writeImage(bim, "D://temp/image"+pageName+".1.png", 300);
				 }
				 if(pageName==5) {
					    PDFRenderer pdfRenderer = new PDFRenderer(document);
			        	BufferedImage bim = pdfRenderer.renderImageWithDPI(pageName, 300, ImageType.RGB).getSubimage(0,1750, 2400 , 1250 );
			       	    //Suffix in filename will be used as the file format
			        	ImageIOUtil.writeImage(bim, "D://temp/image"+pageName+".2.png", 300);
				}
				if(pageName==4) {
					    PDFRenderer pdfRenderer = new PDFRenderer(document);
			        	BufferedImage bim = pdfRenderer.renderImageWithDPI(pageName, 300, ImageType.RGB).getSubimage(0,1750, 2400 , 1200 );
			       	    //Suffix in filename will be used as the file format
			        	ImageIOUtil.writeImage(bim, "D://temp/image"+pageName+".1.png", 300);
				}
				if(pageName==6) {
					    PDFRenderer pdfRenderer = new PDFRenderer(document);
			        	BufferedImage bim = pdfRenderer.renderImageWithDPI(pageName, 300, ImageType.RGB).getSubimage(0,400, 2400 , 1400 );
			       	    //Suffix in filename will be used as the file format
			        	ImageIOUtil.writeImage(bim, "D://temp/image"+pageName+".1.png", 300);
				}
				if(pageName==6) {
						    PDFRenderer pdfRenderer = new PDFRenderer(document);
				        	BufferedImage bim = pdfRenderer.renderImageWithDPI(pageName, 300, ImageType.RGB).getSubimage(0,1750, 2400 , 1250 );
				       	    //Suffix in filename will be used as the file format
				        	ImageIOUtil.writeImage(bim, "D://temp/image"+pageName+".2.png", 300);
				}
				if(pageName==7) {
				    PDFRenderer pdfRenderer = new PDFRenderer(document);
		        	BufferedImage bim = pdfRenderer.renderImageWithDPI(pageName, 300, ImageType.RGB).getSubimage(0,400, 2400 , 1400 );
		       	    //Suffix in filename will be used as the file format
		        	ImageIOUtil.writeImage(bim, "D://temp/image"+pageName+".1.png", 300);
			    }
			    if(pageName==7) {
					    PDFRenderer pdfRenderer = new PDFRenderer(document);
			        	BufferedImage bim = pdfRenderer.renderImageWithDPI(pageName, 300, ImageType.RGB).getSubimage(0,1750, 2400 , 1250 );
			       	    //Suffix in filename will be used as the file format
			        	ImageIOUtil.writeImage(bim, "D://temp/image"+pageName+".2.png", 300);
			  }
			    if(pageName==8) {
				    PDFRenderer pdfRenderer = new PDFRenderer(document);
		        	BufferedImage bim = pdfRenderer.renderImageWithDPI(pageName, 300, ImageType.RGB).getSubimage(0,400, 2400 , 1400 );
		       	    //Suffix in filename will be used as the file format
		        	ImageIOUtil.writeImage(bim, "D://temp/image"+pageName+".1.png", 300);
			    }
			    if(pageName==8) {
					    PDFRenderer pdfRenderer = new PDFRenderer(document);
			        	BufferedImage bim = pdfRenderer.renderImageWithDPI(pageName, 300, ImageType.RGB).getSubimage(0,1750, 2400 , 1250 );
			       	    //Suffix in filename will be used as the file format
			        	ImageIOUtil.writeImage(bim, "D://temp/image"+pageName+".2.png", 300);
			  }
			    if(pageName==9) {
				    PDFRenderer pdfRenderer = new PDFRenderer(document);
		        	BufferedImage bim = pdfRenderer.renderImageWithDPI(pageName, 300, ImageType.RGB).getSubimage(0,400, 2400 , 1400 );
		       	    //Suffix in filename will be used as the file format
		        	ImageIOUtil.writeImage(bim, "D://temp/image"+pageName+".1.png", 300);
			    }
			    if(pageName==9) {
					    PDFRenderer pdfRenderer = new PDFRenderer(document);
			        	BufferedImage bim = pdfRenderer.renderImageWithDPI(pageName, 300, ImageType.RGB).getSubimage(0,1750, 2400 , 1250 );
			       	    //Suffix in filename will be used as the file format
			        	ImageIOUtil.writeImage(bim, "D://temp/image"+pageName+".2.png", 300);
			  }
		        	
			    if(pageName==10) {
				    PDFRenderer pdfRenderer = new PDFRenderer(document);
		        	BufferedImage bim = pdfRenderer.renderImageWithDPI(pageName, 300, ImageType.RGB).getSubimage(0,400, 2400 , 1400 );
		       	    //Suffix in filename will be used as the file format
		        	ImageIOUtil.writeImage(bim, "D://temp/image"+pageName+".1.png", 300);
			    }
			    if(pageName==10) {
					    PDFRenderer pdfRenderer = new PDFRenderer(document);
			        	BufferedImage bim = pdfRenderer.renderImageWithDPI(pageName, 300, ImageType.RGB).getSubimage(0,1750, 2400 , 1250 );
			       	    //Suffix in filename will be used as the file format
			        	ImageIOUtil.writeImage(bim, "D://temp/image"+pageName+".2.png", 300);
			  }
			    if(pageName==11) {
				    PDFRenderer pdfRenderer = new PDFRenderer(document);
		        	BufferedImage bim = pdfRenderer.renderImageWithDPI(pageName, 300, ImageType.RGB).getSubimage(0,400, 2400 , 1400 );
		       	    //Suffix in filename will be used as the file format
		        	ImageIOUtil.writeImage(bim, "D://temp/image"+pageName+".1.png", 300);
			    }
			    if(pageName==11) {
					    PDFRenderer pdfRenderer = new PDFRenderer(document);
			        	BufferedImage bim = pdfRenderer.renderImageWithDPI(pageName, 300, ImageType.RGB).getSubimage(0,1750, 2400 , 1250 );
			       	    //Suffix in filename will be used as the file format
			        	ImageIOUtil.writeImage(bim, "D://temp/image"+pageName+".2.png", 300);
			  }
			    if(pageName==12) {
				    PDFRenderer pdfRenderer = new PDFRenderer(document);
		        	BufferedImage bim = pdfRenderer.renderImageWithDPI(pageName, 300, ImageType.RGB).getSubimage(0,400, 2400 , 1400 );
		       	    //Suffix in filename will be used as the file format
		        	ImageIOUtil.writeImage(bim, "D://temp/image"+pageName+".1.png", 300);
			    }
			    if(pageName==12) {
					    PDFRenderer pdfRenderer = new PDFRenderer(document);
			        	BufferedImage bim = pdfRenderer.renderImageWithDPI(pageName, 300, ImageType.RGB).getSubimage(0,1750, 2400 , 1250 );
			       	    //Suffix in filename will be used as the file format
			        	ImageIOUtil.writeImage(bim, "D://temp/image"+pageName+".2.png", 300);
			  }
			    if(pageName==13) {
				    PDFRenderer pdfRenderer = new PDFRenderer(document);
		        	BufferedImage bim = pdfRenderer.renderImageWithDPI(pageName, 300, ImageType.RGB).getSubimage(0,400, 2400 , 1400 );
		       	    //Suffix in filename will be used as the file format
		        	ImageIOUtil.writeImage(bim, "D://temp/image"+pageName+".1.png", 300);
			    }
			    if(pageName==13) {
					    PDFRenderer pdfRenderer = new PDFRenderer(document);
			        	BufferedImage bim = pdfRenderer.renderImageWithDPI(pageName, 300, ImageType.RGB).getSubimage(0,1750, 2400 , 1250 );
			       	    //Suffix in filename will be used as the file format
			        	ImageIOUtil.writeImage(bim, "D://temp/image"+pageName+".2.png", 300);
			    }
			    if(pageName==14) {
				    PDFRenderer pdfRenderer = new PDFRenderer(document);
		        	BufferedImage bim = pdfRenderer.renderImageWithDPI(pageName, 300, ImageType.RGB).getSubimage(0,400, 2400 , 1400 );
		       	    //Suffix in filename will be used as the file format
		        	ImageIOUtil.writeImage(bim, "D://temp/image"+pageName+".1.png", 300);
			    }
			    if(pageName==14) {
					    PDFRenderer pdfRenderer = new PDFRenderer(document);
			        	BufferedImage bim = pdfRenderer.renderImageWithDPI(pageName, 300, ImageType.RGB).getSubimage(0,1750, 2400 , 1250 );
			       	    //Suffix in filename will be used as the file format
			        	ImageIOUtil.writeImage(bim, "D://temp/image"+pageName+".2.png", 300);
			    }
			  
		    } catch (Exception e) {
		        e.printStackTrace();
		    }

		}

		private void parsedRelatorioTabular(PDDocument document, int numberOfPages) throws IOException {

			
			PDFTextStripperByArea stripper = new PDFTextStripperByArea();
			stripper.setSortByPosition(true);
			int hx = 140;
			Boolean flagStatus=false;
			for (int i = 0; i <= 22; i++) {
				Rectangle rect1 = new Rectangle(30, hx + (i * 20), 40, 20);
				Rectangle rect2 = new Rectangle(65, hx + (i * 20), 40, 20);
				Rectangle rect3 = new Rectangle(100, hx + (i * 20), 40, 20);
				Rectangle rect4 = new Rectangle(135, hx + (i * 20), 40, 20);
				Rectangle rect5 = new Rectangle(170, hx + (i * 20), 40, 20);
				Rectangle rect6 = new Rectangle(205, hx + (i * 20), 40, 20);
				Rectangle rect7 = new Rectangle(245, hx + (i * 20), 40, 20);
				Rectangle rect8 = new Rectangle(285, hx + (i * 20), 40, 20);
				Rectangle rect9 = new Rectangle(320, hx + (i * 20), 40, 20);
				Rectangle rect10 = new Rectangle(365, hx + (i * 20), 40, 20);
				Rectangle rect11 = new Rectangle(400, hx + (i * 20), 40, 20);
				Rectangle rect12 = new Rectangle(440, hx + (i * 20), 40, 20);
				Rectangle rect13 = new Rectangle(480, hx + (i * 20), 40, 20);
				Rectangle rect14 = new Rectangle(515, hx + (i * 20), 40, 20);
				Rectangle rect15 = new Rectangle(565, hx + (i * 20), 40, 20);
				stripper.addRegion("row1column1", rect1);
				stripper.addRegion("row1column2", rect2);
				stripper.addRegion("row1column3", rect3);
				stripper.addRegion("row1column4", rect4);
				stripper.addRegion("row1column5", rect5);
				stripper.addRegion("row1column6", rect6);
				stripper.addRegion("row1column7", rect7);
				stripper.addRegion("row1column8", rect8);
				stripper.addRegion("row1column9", rect9);
				stripper.addRegion("row1column10", rect10);
				stripper.addRegion("row1column11", rect11);
				stripper.addRegion("row1column12", rect12);
				stripper.addRegion("row1column13", rect13);
				stripper.addRegion("row1column14", rect14);
				stripper.addRegion("row1column15", rect15);
				PDPage firstPage = document.getPages().get(2);
				stripper.extractRegions(firstPage);
               if(flagStatus) {
            	   relatorioTabular.setId(MongoUtils.getUId());
            	   relatorioTabular.setHora(stripper.getTextForRegion("row1column1"));
            	   relatorioTabular.setFcmin(stripper.getTextForRegion("row1column2"));
            	   relatorioTabular.setFcmed(stripper.getTextForRegion("row1column3"));
            	   relatorioTabular.setFcmax(stripper.getTextForRegion("row1column4"));
            	   relatorioTabular.setQRSs(stripper.getTextForRegion("row1column5"));
            	   relatorioTabular.setViso(stripper.getTextForRegion("row1column6"));
            	   relatorioTabular.setVpar(stripper.getTextForRegion("row1column7"));
            	   relatorioTabular.setTaqV(stripper.getTextForRegion("row1column8"));
            	   relatorioTabular.setTotV(stripper.getTextForRegion("row1column9"));
            	   relatorioTabular.setSviso(stripper.getTextForRegion("row1column10"));
            	   relatorioTabular.setSvpar(stripper.getTextForRegion("row1column11"));
            	   relatorioTabular.setTaqSV(stripper.getTextForRegion("row1column12"));
            	   relatorioTabular.setTotSV(stripper.getTextForRegion("row1column13"));
            	   relatorioTabular.setFausas(stripper.getTextForRegion("row1column14"));
            	   
            	   relatorioTabularRepository.insert(relatorioTabular);
               }
               flagStatus=true;
			}
			
		}

		public static void readParaFromPDF(PDDocument document, int pageNoStart, int pageNoEnd, int noOfColumnsInTable) {
			ArrayList<String[]> objArrayList = new ArrayList<>();
			try {

				if (!document.isEncrypted()) {
					PDFTextStripperByArea stripper = new PDFTextStripperByArea();
					stripper.setSortByPosition(true);
					PDFTextStripper tStripper = new PDFTextStripper();
					tStripper.setStartPage(pageNoStart);
					tStripper.setEndPage(pageNoEnd);
					String pdfFileInText = tStripper.getText(document);
					// split by whitespace

					String Documentlines[] = pdfFileInText.split("\\r?\\n");
					for (String line : Documentlines) {
						String lineArr[] = line.split("\\s+");
						if (lineArr.length == noOfColumnsInTable) {
							for (String linedata : lineArr) {
								System.out.print(linedata + "             ");
							}
							System.out.println("");
							objArrayList.add(lineArr);
						}
					}
				}
			} catch (Exception e) {
				System.out.println("Exception " + e);
			}

		}

		@Override
		protected void writeString(String text, List<TextPosition> textPositions) throws IOException {

			if ("1 - Dados do Exame".equals(text.trim())) {
				dadosdoExameStart = true;
				dadosdoExameEnd = false;
			}

			if ("2 - Dados do Paciente".equals(text.trim())) {
				dadosDoPacienteStart = true;
				dadosdoExameEnd = true;
			}
			if ("3 - Médico Solicitante".equals(text.trim())) {
				medicaSolicitanteStart = true;
				dadosDoPacienteEnd = true;
			}
			if ("4 - Resumo Estatístico".equals(text.trim())) {
				medicaSolicitanteEnd = true;
				resumoEstatsticoStart = true;
			}
			if ("5 - Laudo Médico".equals(text.trim())) {
				resumoEstatsticoEnd = true;
				laudMedicoStart = true;
			}
			if (ApplicationConstants.RELAT_TAB.equals(text.trim())) {
				ludoMedicoEnd= true;
				laudMedicoStart = true;
			}
			PDColor style = null;
			float fontsize = 0;
			for (TextPosition textPosition : textPositions) {
				style = determineStyleColor(textPosition);
				fontsize = determineFontSize(textPosition);
				System.out.print("...." + textPosition);
				if (!style.equals(currentStyle)) {
					output.write(style.getComponents().toString());
					currentStyle = style;
					textBuilder = new StringBuilder(EMPTY_STRING);
				}

			}

			//
			if (!isPageLine(text)) {
				processDadosDoExame(text, style, fontsize, linenumber);
				processDadosDoPaciente(text, style, fontsize, linenumber);
				processMedicoSolicitante(text, style, fontsize, linenumber);
				processResumoEstatistico(text, style, fontsize, linenumber);
				processLaudoMedico(text, style, fontsize, linenumber);
				output.write(text);

			}
			linenumber++;
		}

		private boolean isPageLine(String text) {
			return text.toLowerCase().startsWith(page_ignore);
		}

		private float determineFontSize(TextPosition textPosition) {
			return textPosition.getFontSize();

		}

	

		private void processLaudoMedico(String text, PDColor style, float fontsize, int linenumber) {
			if (laudMedicoStart && (!"5 - Laudo Médico".equals(text.trim())) && !ludoMedicoEnd) {
				if (!text.endsWith(":")) { 
					  if(ApplicationConstants.ALTER_MORFOL.equals(previousText)) {
						 
						  String tempStr =  laudoMedico.getAlterMorDeSt();
							if (tempStr != null) {
								tempStr = tempStr + " " + text.trim();
								 laudoMedico.setAlterMorDeSt(text);
							} else {
								 laudoMedico.setAlterMorDeSt(text);
							}
					  }
					  if(ApplicationConstants.COMPRO_CLINICO.equals(previousText)) {
						 
						  String tempStr = laudoMedico.getCompoClin();
							if (tempStr != null) {
								tempStr = tempStr + " " + text.trim();
								laudoMedico.setCompoClin(tempStr);
							} else {
								 laudoMedico.setCompoClin(text);
							}
					  }
					  if(ApplicationConstants.COMENTARIOS.equals(previousText)) {
						 
						  String tempStr = laudoMedico.getComent();
							if (tempStr != null) {
								tempStr = tempStr + " " + text.trim();
								laudoMedico.setComent(tempStr);
							} else {
								 laudoMedico.setComent(text);
							}
					  }
					  if(ApplicationConstants.REFERENCIA.equals(previousText)) {
						
						  String tempStr = laudoMedico.getRefer();
							if (tempStr != null) {
								tempStr = tempStr + " " + text.trim();
								laudoMedico.setRefer(tempStr);
							} else {
								  laudoMedico.setRefer(text);
							}
					  }
				
				 
			 }else {
				   previousText = text;
				  
			}}

		}

		private void processDadosDoExame(String text, PDColor style, float fontsize, int linenumber) {
			if (dadosdoExameStart && (!"1 - Dados do Exame".equals(text.trim())) && !dadosdoExameEnd) {
				if (!text.endsWith(":")) {
					if (ApplicationConstants.DATA_DO_EXAME.equals(previousText)) {
						dde.setDataDoExame(text.trim());
						
					}
					if (linenumber == 10) {
						dde.setnDOExame(text.trim());
					}
					if (ApplicationConstants.PROTOCOLO.equals(previousText)) {
						dde.setProtocolo(text.trim());
					}
					if (ApplicationConstants.CODIGO_1.equals(previousText)) {
						dde.setCodigo(text.trim());
					}
				}
			
				previousText = text;
			}
			
			

		}

		private void processDadosDoPaciente(String text, PDColor style, float fontsize, int linenumber) {
			if (dadosDoPacienteStart && (!"2 - Dados do Paciente".equals(text.trim())) && !dadosDoPacienteEnd) {
				if (!text.contains(":")) {
					if (ApplicationConstants.NOME.equals(previousText)) {
						ddp.setNome(text.trim());
					}
					if (ApplicationConstants.IDADE.equals(previousText)) {
						ddp.setIdade(text.trim());
					}
					if (ApplicationConstants.SEXO.equals(previousText)) {
						ddp.setSexo(text.trim());
					}
					if (ApplicationConstants.PESO.equals(previousText)) {
						ddp.setPeso(text.trim());
					}
					if (ApplicationConstants.FUMANTE.equals(previousText)) {
						ddp.setFumante(text.trim());
					}
					if (ApplicationConstants.MOTIVO_DO_EXAME.equals(previousText)) {
						ddp.setMotivoDoExame(text.trim());
					}
					if (ApplicationConstants.ALTURA.equals(previousText)) {
						ddp.setMotivoDoExame(text.trim());
					}

				}
				previousText = text;
			}

		}

		private void processMedicoSolicitante(String text, PDColor style, float fontsize, int linenumber) {
			if (medicaSolicitanteStart && (!"3 - Médico Solicitante".equals(text.trim())) && !medicaSolicitanteEnd) {
				if (!text.contains(":")) {
					if (ApplicationConstants.NOME.equals(previousText)) {
						ms.setNome(text.trim());
					}
					if (ApplicationConstants.FAX.equals(previousText)) {
						ms.setFax(text.trim());
					}
					if (ApplicationConstants.TEL.equals(previousText)) {
						ms.setTel(text.trim());
					}
					if (ApplicationConstants.CLNICA.equals(previousText)) {
						ms.setClnica(text.trim());
					}

				}
				previousText = text;
			}

		}

		private void processResumoEstatistico(String text, PDColor style, float fontsize, int linenumber) {
			if (resumoEstatsticoStart && (!"4 - Resumo Estatístico".equals(text.trim())) && !resumoEstatsticoEnd) {
				if (!text.endsWith(":") ) {
					if (ApplicationConstants.TOTAIS_DURAO.equals(previousText)) {
						totais.setTotaisDurao(text.trim());
					}
					if (ApplicationConstants.FREQ_CARD_MIN.equals(previousText)) {
						String tempStr = frequenciaCardiaca.getFrequenciaCardiacaMin();
						if (tempStr != null) {
							tempStr = tempStr + " " + text.trim();
							frequenciaCardiaca.setFrequenciaCardiacaMin(tempStr);
						} else {
							frequenciaCardiaca.setFrequenciaCardiacaMin(text.trim());
						}
					}
					if (ApplicationConstants.TOTAIS_TOTAL_DeQRS.equals(previousText)) {
						totais.setTotaisTotalDeQRS(text.trim());
					}
					if (ApplicationConstants.RREQ_CARD_MEDIA.equals(previousText)) {
						String tempStr = frequenciaCardiaca.getFrequenciaCardiacaMedia();
						if (tempStr != null) {
							tempStr = tempStr + " " + text.trim();
							frequenciaCardiaca.setFrequenciaCardiacaMedia(tempStr);
						} else {
							frequenciaCardiaca.setFrequenciaCardiacaMedia(text.trim());
						}
					}
					if (ApplicationConstants.TOTAIS_ECT_VENT.equals(previousText)) {
						String tempStr = totais.getTotaisEctopicosVentriculares();
						if (tempStr != null) {
							tempStr = tempStr + " " + text.trim();
							totais.setTotaisEctopicosVentriculares(tempStr);
						} else {
							totais.setTotaisEctopicosVentriculares(text.trim());
						}
					}
					if (ApplicationConstants.RREQ_CARD_MAX.equals(previousText)) {
						String tempStr = frequenciaCardiaca.getFrequenciaCardiacaMax();
						if (tempStr != null) {
							tempStr = tempStr + " " + text.trim();
							frequenciaCardiaca.setFrequenciaCardiacaMax(tempStr);
						} else {
							frequenciaCardiaca.setFrequenciaCardiacaMax(text.trim());
						}
					}
					if (ApplicationConstants.TOTAIS_ECT_SUP.equals(previousText)) {
						String tempStr = totais.getTotaisEctopicosSupraventriculares();
						if (tempStr != null) {
							tempStr = tempStr + " " + text.trim();
							totais.setTotaisEctopicosSupraventriculares(tempStr);
						} else {
							totais.setTotaisEctopicosSupraventriculares(text.trim());
						}
					}
					if (artefatos == true && ApplicationConstants.TOTAIS_ART.equals(previousText)) {
					/*
					 * if (artefatosCount == 1) { re.setFC120(text.trim()); artefatosCount =
					 * artefatosCount + 1; } else { String tempStr = re.getFC50(); if (tempStr !=
					 * null) { tempStr = tempStr + " " + text.trim(); re.setFC50(tempStr); } else {
					 * re.setFC50(text.trim()); } }
					 */
					}
					if (ApplicationConstants.TOTAIS_ART.equals(previousText) && artefatos!= true) {
						String tempStr = totais.getTotaisArtefatos();
						if (tempStr != null) {
							tempStr = tempStr + " " + text.trim();
							totais.setTotaisArtefatos(tempStr);
						} else {
							totais.setTotaisArtefatos(text.trim());
						}
						artefatos = true;
					}

					if (ApplicationConstants.ARR_VEN_EM4EP_DEBI.equals(previousText)) {
						if (arritmiasVen == 1) {
							 arritmiasVentriculares.setIsoladasDasQuais(text.trim());
						}
						if (arritmiasVen == 4) {
							arritmiasVentriculares.setEmEpisDeBigeminismo(text.trim());
							
						}
						if (arritmiasVen == 6) {
							pausas.setPausas(text.trim());
						
						}
						if (arritmiasVen == 7) {
							  arritmiasVentriculares.setEpisodiosEmPares(text.trim());
						
						}
						if (arritmiasVen == 8) {
							arritmiasVentriculares.setEpisodiosEmPares(text.trim());
							
						}
						if (arritmiasVen == 10) {
							arritmiasVentriculares.setTaquicardias(text.trim());
							
						}

						arritmiasVen = arritmiasVen + 1;

					}
					if (ApplicationConstants.ARR_SUPRAVENT.equals(previousText)) {
						if (arritmiasSup == 1) {
							depressaoDoST.setDepressaoDoSTC1(text.substring(2).trim());
						}
						if (arritmiasSup == 2) {
							depressaoDoST.setDepressaoDoSTC2(text.substring(2).trim());
						}
						if (arritmiasSup == 3) {
							arritmiasSupraventriculares.setIsoladas(text.trim());
						}
						if (arritmiasSup == 5) {
							depressaoDoST.setDepressaoDoSTC3(text.substring(2).trim());
						}
						if (arritmiasSup == 6) {
							arritmiasSupraventriculares.setPareadas(text.trim());
						}
						if (arritmiasSup == 8) {
							arritmiasSupraventriculares.setTaquicardias(text.trim());
						}
						arritmiasSup = arritmiasSup + 1;

					}
					if (ApplicationConstants.MAIOR.equals(previousText)) {

						if (arritmiasSupCount <= 3) {
							String tempStr = arritmiasSupraventriculares.getMaior();
							if (tempStr != null) {
								tempStr = tempStr + " " + text.trim();
								arritmiasSupraventriculares.setMaior(tempStr);
							} else {
								arritmiasSupraventriculares.setMaior(text.trim());
							}
							arritmiasSupCount = arritmiasSupCount + 1;
						} else {
							arritmiasSupCount = 1;
						}

					}
					if (ApplicationConstants.MAIOR_RAPIDA.equals(previousText)) {
						if (arritmiasSupCount <= 3) {
							String tempStr = arritmiasSupraventriculares.getRaisRapida();
							if (tempStr != null) {
								tempStr = tempStr + " " + text.trim();
								arritmiasSupraventriculares.setRaisRapida(tempStr);
							} else {
								arritmiasSupraventriculares.setRaisRapida(text.trim());
							}
							arritmiasSupCount = arritmiasSupCount + 1;
						} else {
							arritmiasSupCount = 1;
						}

					}
					if (ApplicationConstants.MAIOR_LENTA.equals(previousText)) {
						  if (arritmiasSupCount <= 3 ) {
							String tempStr = arritmiasSupraventriculares.getMaisLenta();
							if (tempStr != null) {
								tempStr = tempStr + " " + text.trim();
								arritmiasSupraventriculares.setMaisLenta(tempStr);
							} else {
								arritmiasSupraventriculares.setMaisLenta(text.trim());
							}
							arritmiasSupCount = arritmiasSupCount + 1;
						} 
					}

				} else {
					previousText = text;
				}
				if(text.contains("C1:") ) {
						elevacaoDoST.setElevacaoDoSTC1(text.substring(2).trim());
					
				}
				if(text.contains("C2:") ) {
					elevacaoDoST.setElevacaoDoSTC2(text.substring(2).trim());
				}
				if(text.contains("C3:") ) {
					elevacaoDoST.setElevacaoDoSTC3(text.substring(2).trim());
				}

			}

		}

		@Override
		protected void processTextPosition(TextPosition text) {
			super.processTextPosition(text);
			PDColor nonStrokingColor = getGraphicsState().getNonStrokingColor();
			textColorMap.put(text, nonStrokingColor);
		}
}

package com.oasys.onlyofc.controller;

@RestController
public class CallbackController {
  @Autowired CallbackService callbackService;
  @Autowired Path storageBase;

  @PostMapping("/callback")
  public Map<String,Object> onCallback(@RequestBody Callback cb) {
    callbackService.processCallback(cb, data -> {
      if (data.getStatus().isEditable()) {
        String key = data.getKey();
        try(InputStream in = new URL(data.getUrl()).openStream()) {
          Files.copy(in, storageBase.resolve(key), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) { e.printStackTrace(); }
      }
    });
    return Map.of("error",0);
  }
}

package com.example.server.Service.implementation;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Collection;
import java.util.Random;

import com.example.server.Service.ServerService;
import com.example.server.enumeration.Status;
import com.example.server.model.Server;
import com.example.server.repository.ServerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.transaction.Transactional;

import static com.example.server.enumeration.Status.SERVER_DOWN;
import static com.example.server.enumeration.Status.SERVER_UP;
import static java.lang.Boolean.TRUE;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class ServerServiceImpl implements ServerService{
    private final ServerRepository serverRepository;

    @Override
    public Server create(Server server) {
        log.info("Saving new server: {}", server.getName());
        server.setImageUrl(setServerImageUrl());
        return serverRepository.save(server);
    }

    @Override
    public Server ping(String ipAddress) throws IOException {
        log.info("Pinging server IP: {}", ipAddress);
        Server server = serverRepository.findByIpAddress(ipAddress);
        InetAddress address = InetAddress.getByName(ipAddress);
        server.setStatus(address.isReachable(10000) ? SERVER_UP : SERVER_DOWN);
        serverRepository.save(server);
        return server;
    }

    @Override
    public Collection<Server> list(int limit) {
        log.info("Fetching all servers");
        return serverRepository.findAll(PageRequest.of(0, limit)).toList();
    }

    @Override
    public Server get(Long id) {
        log.info("Fetching server by id: {}", id);
        return serverRepository.findById(id).get();
    }

    @Override
    public Server update(Server server) {
        log.info("Updating server: {}", server.getName());
        return serverRepository.save(server);
    }

    @Override
    public Boolean delete(Long id) {
        log.info("Deleting server by id: {}", id);
        serverRepository.deleteById(id);
        return TRUE;
    }

    private String setServerImageUrl() {
        String[] imageNames = { "server_1.png", "server_2.png", "server_3.png" };
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/server/image/" + imageNames[new Random().nextInt(4)]).toUriString();
    }
}